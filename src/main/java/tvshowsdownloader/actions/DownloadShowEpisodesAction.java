package tvshowsdownloader.actions;

import tvshowsdownloader.exceptions.DownloadException;
import tvshowsdownloader.services.DownloadService;

public class DownloadShowEpisodesAction extends APIAction {

	private Integer id;

	@Override
	public String execute() {
		try {
			new DownloadService().downloadShowEpisodes(id);
		} catch (DownloadException e) {
			return returnDownloadErrors(e.getMessages());
		} catch (Exception e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
