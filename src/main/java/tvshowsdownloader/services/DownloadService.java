package tvshowsdownloader.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kodishowsapi.exceptions.KodiEpisodesFetchingException;
import kodishowsapi.exceptions.KodiShowFetchingException;
import plexapi.exceptions.PlexShowFetchingException;
import qbittorrentapi.exceptions.QBitLoginException;
import qbittorrentapi.exceptions.QBitParametersException;
import qbittorrentapi.exceptions.QBitURLException;
import thepiratebayapi.exceptions.TPBayURLException;
import thepiratebayapi.services.TPBayAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.exceptions.PropertyException;
import tvtimeapi.beans.TVTimeEpisode;
import tvtimeapi.beans.TVTimeSeason;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.beans.TVTimeWatchlist;
import tvtimeapi.exceptions.TVTimeLoginException;
import tvtimeapi.exceptions.TVTimeURLException;
import tvtimeapi.exceptions.TVTimeWatchlistFetchingException;
import tvtimeapi.services.TVTimeAPI;

public class DownloadService {
	Properties properties;

	TVTimeAPI tvTimeAPI;

	TPBayAPI tpBayAPI;

	DownloaderService downloaderService;

	LibraryService libraryService;

	public DownloadService() throws FileNotFoundException, IOException, TVTimeURLException, TVTimeLoginException, TPBayURLException, QBitLoginException, QBitURLException, QBitParametersException, PropertyException, Exception {
		super();
		properties = new Properties();
		InputStream is = new FileInputStream("tvshowdownloader.properties");
		properties.load(is);

		tvTimeAPI = new TVTimeAPI(properties.getProperty("tvtime.url", "https://www.tvtime.com"),
				properties.getProperty("tvtime.username"), properties.getProperty("tvtime.password"));
		tpBayAPI = new TPBayAPI(properties.getProperty("tpbay.url"));
		downloaderService = new DownloaderService(properties);
		libraryService = new LibraryService(properties);
	}

	public void getEpisodesToDownload() throws TVTimeWatchlistFetchingException, TVTimeURLException, KodiShowFetchingException, KodiEpisodesFetchingException, PlexShowFetchingException {
		List<Show> watchlist = getTvTimeShows();
		List<Show> library = libraryService.getShows();
		List<Show> downloads = downloaderService.getShows();
	}

	public void downloadEpisodes(List<Show> episodes) {
		// TODO
	}

	private List<Show> getTvTimeShows() throws TVTimeWatchlistFetchingException, TVTimeURLException {
		List<Show> shows = new ArrayList<>();

		for (TVTimeShow tvTimeShow : tvTimeAPI.getWatchlist()) {
			Show show = new Show();
			show.setName(tvTimeShow.getName());
			show.setBanner(tvTimeShow.getBanner());

			List<Episode> episodes = new ArrayList<>();
			for (Map.Entry<Integer, TVTimeSeason> seasonEntry : tvTimeShow.getSeasons().entrySet()) {
				for (Map.Entry<Integer, TVTimeEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
					episodes.add(new Episode(seasonEntry.getKey(), episodeEntry.getKey(), episodeEntry.getValue().getTitle()));
				}
			}

			shows.add(show);
		}

		return shows;
	}
}
