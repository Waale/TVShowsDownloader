package tvshowsmonitoring.actions;

import kodishowsapi.beans.KodiShow;
import kodishowsapi.services.KodiAPI;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.beans.TVTimeWatchlist;
import tvtimeapi.services.TVTimeAPI;

public class HomeAction extends Action {
	TVTimeWatchlist watchlist;

	String tvstRemember;
	
	@SuppressWarnings("unused")
	public String execute() {
		TVTimeAPI tvTimeApi = new TVTimeAPI();
		watchlist = new TVTimeWatchlist();

		TVTimeWatchlist tvTimeWatchlist = tvTimeApi.getWatchlist(tvstRemember);
		for (TVTimeShow tvTimeShow : tvTimeWatchlist) {
			KodiShow kodiShow = KodiAPI.getShowByTitle(tvTimeShow.getName());
			// TODO Verifications

			watchlist.add(tvTimeShow);
		}
		
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
