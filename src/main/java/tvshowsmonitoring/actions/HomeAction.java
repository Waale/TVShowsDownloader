package tvshowsmonitoring.actions;

import tvtimeapi.beans.TVTimeSimplifiedWatchlist;
import tvtimeapi.beans.TVTimeWatchlist;
import tvtimeapi.services.TVTimeAPI;

public class HomeAction extends Action {
	TVTimeSimplifiedWatchlist simplifiedWatchlist;
	
	@SuppressWarnings("unused")
	public String execute() {
		TVTimeAPI tvTimeApi = new TVTimeAPI();
		
		String tvstRemember = getParamValue("tvstRemember");		
		simplifiedWatchlist = tvTimeApi.getSimplifiedWatchlist(tvstRemember);
		
		return SUCCESS;
	}

	public TVTimeSimplifiedWatchlist getSimplifiedWatchlist() {
		return simplifiedWatchlist;
	}

	public void setSimplifiedWatchlist(TVTimeSimplifiedWatchlist simplifiedWatchlist) {
		this.simplifiedWatchlist = simplifiedWatchlist;
	}
}
