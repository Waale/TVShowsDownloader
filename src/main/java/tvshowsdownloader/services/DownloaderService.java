package tvshowsdownloader.services;

import java.util.Properties;

import qbittorrentapi.services.QBitAPI;
import synologydownloadstationapi.services.SynoDSAPI;

class DownloaderService {
	QBitAPI qBitAPI;
	
	SynoDSAPI synoDsAPI;

	protected DownloaderService(Properties properties) {
		super();
		String downloader = properties.getProperty("downloader");
		
	}
}
