package tvshowsdownloader.actions;

import tvshowsdownloader.exceptions.DownloadException;
import tvshowsdownloader.services.DownloadService;

public class DownloadAllEpisodesAction extends APIAction {
	@Override
	public String execute() {
		try {
			new DownloadService().downloadAllEpisodes();
		} catch (DownloadException e) {
			return returnDownloadErrors(e.getMessages());
		} catch (Exception e) {
			return returnError(e);
		}

		return SUCCESS;
	}
}
