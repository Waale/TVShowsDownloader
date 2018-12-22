package tvshowsdownloader.actions;

import tvshowsdownloader.services.DownloadService;

public class DownloadAllEpisodesAction extends APIAction {
	@Override
	public String execute() {
		try {
			new DownloadService().downloadAllEpisodes();
		} catch (Exception e) {
			return returnError(e);
		}

		return SUCCESS;
	}
}
