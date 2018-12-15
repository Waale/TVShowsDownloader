package tvshowsdownloader.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import qbittorrentapi.exceptions.QBitLoginException;
import qbittorrentapi.exceptions.QBitParametersException;
import qbittorrentapi.exceptions.QBitURLException;
import qbittorrentapi.services.QBitAPI;
import synologydownloadstationapi.services.SynoDSAPI;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.beans.Torrent;
import tvshowsdownloader.enums.Downloader;
import tvshowsdownloader.exceptions.PropertyException;

class DownloaderService {
	private Downloader downloader;

	private QBitAPI qBitAPI;
	
	private SynoDSAPI synoDsAPI;

	protected DownloaderService(Properties properties) throws QBitLoginException, QBitURLException, QBitParametersException, PropertyException, Exception {
		super();
		downloader = Downloader.getByValue(properties.getProperty("downloader"));
		switch (downloader) {
			case QBITTORRENT:
				qBitAPI = new QBitAPI(properties.getProperty("qbittorrent.url"), properties.getProperty("qbittorrent.username"), properties.getProperty("qbittorrent.password"));
				break;
			case SYNODS:
				synoDsAPI = new SynoDSAPI(properties.getProperty("synology.url"), properties.getProperty("synology.username"), properties.getProperty("synology.password"));
				break;
			default:
				throw new PropertyException("downloader", properties.getProperty("downloader"));
		}
	}

	public List<Show> getShows() {
		List<Show> shows = new ArrayList<>();

		return shows;
	}

	public List<Torrent> getQBitTorrents() {

	}

	public List<Torrent> getSynoDSTorrents() {

	}
}
