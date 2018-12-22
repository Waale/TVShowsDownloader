package tvshowsdownloader.actions;

import tvshowsdownloader.beans.Show;
import tvshowsdownloader.services.DownloadService;

import java.util.List;

public class DownloadEpisodesAction extends APIAction {

	private List<Show> shows;

	@Override
	public String execute() {
		try {
			new DownloadService().downloadShows(shows);
		} catch (Exception e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}
}
