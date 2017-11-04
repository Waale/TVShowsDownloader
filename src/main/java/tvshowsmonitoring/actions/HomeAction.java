package tvshowsmonitoring.actions;

import tvtimeapi.beans.TVTimeWatchlist;
import tvtimeapi.services.TVTimeAPI;

public class HomeAction extends Action {
	TVTimeWatchlist watchlist;

	String tvstRemember;
	
	@SuppressWarnings("unused")
	public String execute() {
		TVTimeAPI tvTimeApi = new TVTimeAPI();

		watchlist = tvTimeApi.getWatchlist(tvstRemember);
		
		return SUCCESS;
	}

	public TVTimeWatchlist getWatchlist() {
		return watchlist;
	}

	public void setWatchlist(TVTimeWatchlist watchlist) {
		this.watchlist = watchlist;
	}

	public String getTvstRemember() {
		return tvstRemember;
	}

	public void setTvstRemember(String tvstRemember) {
		this.tvstRemember = tvstRemember;
	}
}
