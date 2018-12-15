package tvshowsdownloader.services;

import kodishowsapi.beans.KodiEpisode;
import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;
import kodishowsapi.exceptions.KodiDatabaseConnectionException;
import kodishowsapi.exceptions.KodiEpisodesFetchingException;
import kodishowsapi.exceptions.KodiShowFetchingException;
import kodishowsapi.services.KodiAPI;
import plexapi.beans.PlexEpisode;
import plexapi.beans.PlexSeason;
import plexapi.beans.PlexShow;
import plexapi.exceptions.PlexShowFetchingException;
import plexapi.services.PlexAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.enums.Library;
import tvshowsdownloader.exceptions.PropertyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

class LibraryService {
	private Library library;

	private KodiAPI kodiAPI;
	
	private PlexAPI plexAPI;

	protected LibraryService(Properties properties) throws KodiDatabaseConnectionException, PropertyException {
		super();
		library = Library.getByValue(properties.getProperty("library"));
		switch (library) {
			case KODI:
				kodiAPI = new KodiAPI("kodi.path");
				break;
			case PLEX:
				plexAPI = new PlexAPI("plex.url", "plex.token");
				break;
			default:
				throw new PropertyException("library", properties.getProperty("library"));
		}
	}

	public List<Show> getShows() throws KodiShowFetchingException, KodiEpisodesFetchingException, PlexShowFetchingException {
		List<Show> shows = null;

		switch (library) {
			case KODI:
				shows = getKodiShows();
			case PLEX:
				shows = getPlexShows();
		}

		return shows;
	}

	private List<Show> getKodiShows() throws KodiShowFetchingException, KodiEpisodesFetchingException {
		List<Show> shows = new ArrayList<>();

		for (KodiShow kodiShow : kodiAPI.getShows()) {
			Show show = new Show();
			show.setName(kodiShow.getTitle());

			List<Episode> episodes = new ArrayList<>();
			for (Map.Entry<Integer, KodiSeason> seasonEntry : kodiShow.getSeasons().entrySet()) {
				for (Map.Entry<Integer, KodiEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
					episodes.add(new Episode(seasonEntry.getKey(), episodeEntry.getKey(), episodeEntry.getValue().getTitle()));
				}
			}

			shows.add(show);
		}

		return shows;
	}

	private List<Show> getPlexShows() throws PlexShowFetchingException {
		List<Show> shows = new ArrayList<>();

		for (PlexShow plexShow : plexAPI.getShows()) {
			Show show = new Show();
			show.setName(plexShow.getTitle());

			List<Episode> episodes = new ArrayList<>();
			for (Map.Entry<Integer, PlexSeason> seasonEntry : plexShow.getSeasons().entrySet()) {
				for (Map.Entry<Integer, PlexEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
					episodes.add(new Episode(seasonEntry.getKey(), episodeEntry.getKey(), episodeEntry.getValue().getTitle()));
				}
			}

			shows.add(show);
		}

		return shows;
	}
}
