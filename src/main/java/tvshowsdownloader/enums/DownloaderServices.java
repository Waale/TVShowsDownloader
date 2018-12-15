package tvshowsdownloader.enums;

public enum DownloaderServices {
	QBITTORRENT("qbittorrent"),
	SYNODS("synology-ds")
	;
	
	String value;

	private DownloaderServices(String value) {
		this.value = value;
	}
	
	public static getByValue(String value) {
		if (value == null) {
			return null;
		} else {
			
		}
	}
}
