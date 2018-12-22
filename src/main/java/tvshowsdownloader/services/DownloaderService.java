package tvshowsdownloader.services;

import qbittorrentapi.services.QBitAPI;
import synologydownloadstationapi.services.SynoDSAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.beans.Torrent;
import tvshowsdownloader.enums.Downloader;
import tvshowsdownloader.exceptions.PropertyException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class DownloaderService {
	private Downloader downloader;

	private QBitAPI qBitAPI;
	
	private SynoDSAPI synoDsAPI;

	protected DownloaderService(PropertiesService propertiesService) throws Exception {
		super();

		downloader = Downloader.getByValue(propertiesService.getProperty("downloader"));
		if (downloader == null) {
			throw new PropertyException("downloader", propertiesService.getProperty("downloader"));
		}

		switch (downloader) {
			case QBITTORRENT:
				qBitAPI = new QBitAPI(propertiesService.getProperty("qbittorrent.url"), propertiesService.getProperty("qbittorrent.username"), propertiesService.getProperty("qbittorrent.password"));
				break;
			case SYNODS:
				synoDsAPI = new SynoDSAPI(propertiesService.getProperty("synology-ds.url"), propertiesService.getProperty("synology-ds.username"), propertiesService.getProperty("synology-ds.password"));
				break;
		}
	}

	public List<Show> getShows() throws Exception {
		List<Torrent> torrents = new ArrayList<>();

		switch (downloader) {
			case QBITTORRENT:
				torrents = getQBitTorrents();
				break;
			case SYNODS:
				torrents = getSynoDSTorrents();
				break;
		}

		Map<String, Show> shows = new HashMap<>();
		Pattern pattern  = Pattern.compile("(.*) S(\\d+)E(\\d+)");
		for (Torrent torrent : torrents) {
			Matcher matcher = pattern.matcher(torrent.getName());
			if (matcher.matches()) {
				String showName = matcher.group(0);

				Episode episode = new Episode();
				episode.setSeason(Integer.valueOf(matcher.group(1)));
				episode.setNumber(Integer.valueOf(matcher.group(2)));

				Show show = shows.get(showName);
				if (show == null) {
					show = new Show();
					show.setName(showName);
				}
				show.addEpisode(episode);
				shows.put(showName, show);
			}
		}

		return shows.values().stream().collect(Collectors.toList());
	}

	private List<Torrent> getQBitTorrents() throws Exception {
		return qBitAPI.getTorrentList(null).stream().map(t -> new Torrent(t)).collect(Collectors.toList());
	}

	private List<Torrent> getSynoDSTorrents() throws Exception {
		return synoDsAPI.getTorrentList().stream().map(t -> new Torrent(t)).collect(Collectors.toList());
	}

	public void addTorrent(String url, String destination, String name) throws Exception {
		List<String> urls = Arrays.asList(url);

		switch (downloader) {
			case QBITTORRENT:
				addQBitTorrents(urls, destination, name);
				break;
			case SYNODS:
				addSynoDSTorrents(urls, destination);
				break;
		}
	}

	private void addQBitTorrents(List<String> urls, String destination, String name) throws Exception {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("rename", name);
		parameters.put("savePath", destination);

		qBitAPI.addTorrents(urls, parameters);
	}

	private void addSynoDSTorrents(List<String> urls, String destination) throws Exception {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("destination", destination);

		synoDsAPI.addTorrents(urls, destination);
	}
}
