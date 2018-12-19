package tvshowsdownloader.services;

import kodishowsapi.beans.KodiEpisode;
import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;
import kodishowsapi.services.KodiAPI;
import plexapi.beans.PlexEpisode;
import plexapi.beans.PlexSeason;
import plexapi.beans.PlexShow;
import plexapi.services.PlexAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.enums.Library;
import tvshowsdownloader.exceptions.PropertyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class LibraryService {
	private Library library;

	private KodiAPI kodiAPI;
	
	private PlexAPI plexAPI;

	protected LibraryService(PropertiesService propertiesService) throws Exception {
		super();

		library = Library.getByValue(propertiesService.getProperty("library"));
		if (library == null) {
			throw new PropertyException("library", propertiesService.getProperty("library"));
		}

		switch (library) {
			case KODI:
				kodiAPI = new KodiAPI(propertiesService.getProperty("kodi.path"));
				break;
			case PLEX:
				plexAPI = new PlexAPI(propertiesService.getProperty("plex.url"), propertiesService.getProperty("plex.token"));
				break;
		}
	}

	public List<Show> getShows() throws Exception {
		List<Show> shows = null;

		switch (library) {
			case KODI:
				shows = getKodiShows();
				break;
			case PLEX:
				shows = getPlexShows();
				break;
		}

		return shows;
	}

	private List<Show> getKodiShows() throws Exception {
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
			show.setEpisodes(episodes);

			shows.add(show);
		}

		return shows;
	}

	private List<Show> getPlexShows() throws Exception {
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
			show.setEpisodes(episodes);

			shows.add(show);
		}

		return shows;
	}
}
