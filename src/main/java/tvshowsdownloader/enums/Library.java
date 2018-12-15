package tvshowsdownloader.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Library {
	KODI("kodi"),
	PLEX("plex")
	;

	String value;

	Library(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Library getByValue(String value) {
		if (value == null) {
			return null;
		} else {
			return Arrays.stream(Library.values()).collect(Collectors.toMap(Library::getValue, d -> d)).get(value);
		}
	}
}
