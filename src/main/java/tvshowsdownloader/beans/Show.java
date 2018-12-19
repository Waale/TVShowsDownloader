package tvshowsdownloader.beans;

import java.util.ArrayList;
import java.util.List;

public class Show {
	private String name;

	private String banner;
	
	private List<Episode> episodes = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public void addEpisode(Episode episode) {
		episodes.add(episode);
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
}
