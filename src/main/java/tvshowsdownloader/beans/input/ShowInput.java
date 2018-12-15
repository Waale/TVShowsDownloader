package tvshowsdownloader.beans.input;

import java.util.List;

public class ShowInput {
	private String name;
	
	private List<EpisodeInput> episodes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EpisodeInput> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<EpisodeInput> episodes) {
		this.episodes = episodes;
	}
}
