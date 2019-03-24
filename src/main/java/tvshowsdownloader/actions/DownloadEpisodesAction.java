package tvshowsdownloader.actions;

import tvshowsdownloader.beans.Show;
import tvshowsdownloader.exceptions.DownloadException;
import tvshowsdownloader.services.DownloadService;

import java.util.List;

public class DownloadEpisodesAction extends APIAction {

	private List<Show> shows;

	@Override
	public String execute() {
		try {
			new DownloadService().downloadEpisodes(shows);
		} catch (DownloadException e) {
			return returnDownloadErrors(e.getMessages());
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
