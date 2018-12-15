package tvshowsdownloader.actions;

import java.util.List;

import tvshowsdownloader.beans.input.ShowInput;

public class DownloadEpisodesAction extends Action {
	
	private List<ShowInput> data;
	
	private String jsonStringResult;

	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<ShowInput> getData() {
		return data;
	}

	public void setData(List<ShowInput> data) {
		this.data = data;
	}

	public String getJsonStringResult() {
		return jsonStringResult;
	}

	public void setJsonStringResult(String jsonStringResult) {
		this.jsonStringResult = jsonStringResult;
	}

}
