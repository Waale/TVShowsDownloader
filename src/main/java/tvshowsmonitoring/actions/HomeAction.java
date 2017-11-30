package tvshowsmonitoring.actions;

import kodishowsapi.beans.KodiShow;
import kodishowsapi.services.KodiAPI;
import tvshowsmonitoring.services.ShowService;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.beans.TVTimeWatchlist;
import tvtimeapi.services.TVTimeAPI;

public class HomeAction extends Action {
    TVTimeWatchlist watchlist;

    String tvstRemember;

    @SuppressWarnings("unused")
    public String execute() {
        TVTimeAPI tvTimeApi = new TVTimeAPI();
        KodiAPI kodiAPI = new KodiAPI();
        ShowService showService = new ShowService();
        watchlist = new TVTimeWatchlist();

        TVTimeWatchlist tvTimeWatchlist = tvTimeApi.getWatchlist(tvstRemember);
        for (TVTimeShow tvTimeShow : tvTimeWatchlist) {
            KodiShow kodiShow = kodiAPI.getShowAndDetailsByTitle(tvTimeShow.getName());
            if (!showService.allEpisodesAreInLibrary(kodiShow, tvTimeShow)) {
                watchlist.add(tvTimeShow);
            }
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
