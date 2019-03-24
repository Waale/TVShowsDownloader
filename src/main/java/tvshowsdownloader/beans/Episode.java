package tvshowsdownloader.beans;

public class Episode {
	private Integer season;
	
	private Integer number;

	private String name;

	private Boolean watched;

	public Episode() {}

	public Episode(Integer season, Integer number, String name, Boolean watched) {
		this.season = season;
		this.number = number;
		this.name = name;
		this.watched = watched;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getWatched() {
		return watched;
	}

	public void setWatched(Boolean watched) {
		this.watched = watched;
	}

	public String getNumberAsString() {
		return "S" + String.format("%02d", season) + "E" + String.format("%02d", number);
	}
}
