package tvshowsdownloader.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import thepiratebayapi.services.TPBayAPI;
import tvshowsdownloader.beans.input.ShowInput;
import tvtimeapi.exceptions.TVTimeLoginException;
import tvtimeapi.exceptions.TVTimeURLException;
import tvtimeapi.services.TVTimeAPI;

public class DownloadService {
	Properties properties;

	TVTimeAPI tvTimeAPI;

	TPBayAPI tpBayAPI;

	DownloaderService downloaderService;

	LibraryService libraryService;

	public DownloadService() throws FileNotFoundException, IOException, TVTimeURLException, TVTimeLoginException  {
		super();
		properties = new Properties();
		InputStream is = new FileInputStream("tvshowdownloader.properties");
		properties.load(is);

		tvTimeAPI = new TVTimeAPI(properties.getProperty("tvtime.url", "https://www.tvtime.com"),
				properties.getProperty("tvtime.username"), properties.getProperty("tvtime.password"));
	}

	public void downloadEpisodes(List<ShowInput> episodes) {
		// TODO
	}
}
