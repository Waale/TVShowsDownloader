package tvshowsdownloader.beans;

import java.util.ArrayList;
import java.util.List;

public class Show {
	private Integer id;

	private String name;

	private String overview;

	private String banner;

	private String poster;
	
	private List<Episode> episodes = new ArrayList<>();

	private Episode nextEpisode;

	private Integer airedEpisodes;

	private Integer seenEpisodes;

	private Integer downloadedEpisodes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
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

	public Episode getNextEpisode() {
		return nextEpisode;
	}

	public void setNextEpisode(Episode nextEpisode) {
		this.nextEpisode = nextEpisode;
	}

	public Integer getAiredEpisodes() {
		return airedEpisodes;
	}

	public void setAiredEpisodes(Integer airedEpisodes) {
		this.airedEpisodes = airedEpisodes;
	}

	public Integer getSeenEpisodes() {
		return seenEpisodes;
	}

	public void setSeenEpisodes(Integer seenEpisodes) {
		this.seenEpisodes = seenEpisodes;
	}

	public Integer getDownloadedEpisodes() {
		return downloadedEpisodes;
	}

	public void setDownloadedEpisodes(Integer downloadedEpisodes) {
		this.downloadedEpisodes = downloadedEpisodes;
	}
}
