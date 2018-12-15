package tvshowsdownloader.actions;

import java.util.List;

import tvshowsdownloader.beans.Show;

public class DownloadEpisodesAction extends Action {
	
	private List<Show> data;
	
	private String jsonStringResult;

	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<Show> getData() {
		return data;
	}

	public void setData(List<Show> data) {
		this.data = data;
	}

	public String getJsonStringResult() {
		return jsonStringResult;
	}

	public void setJsonStringResult(String jsonStringResult) {
		this.jsonStringResult = jsonStringResult;
	}

}
