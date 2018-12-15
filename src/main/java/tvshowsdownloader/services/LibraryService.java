package tvshowsdownloader.services;

import kodishowsapi.services.KodiAPI;
import plexapi.services.PlexAPI;

public class LibraryService {
	KodiAPI kodiAPI;
	
	PlexAPI plexAPI;

	public LibraryService(KodiAPI kodiAPI) {
		super();
		this.kodiAPI = kodiAPI;
	}

	public LibraryService(PlexAPI plexAPI) {
		super();
		this.plexAPI = plexAPI;
	}
}
