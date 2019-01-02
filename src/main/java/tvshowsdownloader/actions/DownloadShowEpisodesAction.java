package tvshowsdownloader.actions;

import tvshowsdownloader.services.DownloadService;

public class DownloadShowEpisodesAction extends APIAction {

	private String name;

	@Override
	public String execute() {
		try {
			new DownloadService().downloadShowEpisodes(name);
		} catch (Exception e) {
			return returnError(e);
		}

		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
