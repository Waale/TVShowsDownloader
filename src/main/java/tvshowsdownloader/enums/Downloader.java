package tvshowsdownloader.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Downloader {
	QBITTORRENT("qbittorrent"),
	SYNODS("synology-ds")
	;
	
	String value;

	Downloader(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Downloader getByValue(String value) {
		if (value == null) {
			return null;
		} else {
			return Arrays.stream(Downloader.values()).collect(Collectors.toMap(Downloader::getValue, d -> d)).get(value);
		}
	}
}
