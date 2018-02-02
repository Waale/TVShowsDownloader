package tvshowsmonitoring.actions;

import kodishowsapi.beans.KodiShow;
import kodishowsapi.services.KodiAPI;
import qbittorrentapi.services.QBitAPI;
import tvshowsmonitoring.services.KodiService;
import tvshowsmonitoring.services.ShowService;
import tvshowsmonitoring.services.TorrentService;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.beans.TVTimeWatchlist;
import tvtimeapi.services.TVTimeAPI;

import java.io.IOException;

/**
 * Created by Romain on 02/02/2018.
 */
public class AutoDownloadAction extends Action {
    String qBittorrentUrl;

    String tvstRemember;

    public String execute() {
        TVTimeAPI tvTimeApi = new TVTimeAPI();
        KodiAPI kodiAPI = new KodiAPI();
        QBitAPI qBitAPI = new QBitAPI(qBittorrentUrl);
        ShowService showService = new ShowService();
        KodiService kodiService = new KodiService();
        TorrentService torrentService = new TorrentService();

        try {
            qBitAPI.pauseAllTorrents();
        } catch (IOException e) {
            e.printStackTrace();
        }

        torrentService.removeDownloadedEpisodes(qBitAPI);
        kodiService.scanForNewEpisodes();
        kodiService.syncKodiToTvTime();

        TVTimeWatchlist tvTimeWatchlist = tvTimeApi.getWatchlist(tvstRemember);
        for (TVTimeShow tvTimeShow : tvTimeWatchlist) {
            KodiShow kodiShow = kodiAPI.getShowAndDetailsByTitle(tvTimeShow.getName());
            if (!showService.allEpisodesAreInLibrary(kodiShow, tvTimeShow)) {
                try {
                    tvTimeShow.buildSeasons(tvstRemember);
                    showService.downloadEpisodes(showService.getNotDownloadingEpisodes(qBitAPI.getTorrentList(), showService.getUnownedEpisodes(kodiShow, tvTimeShow)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            qBitAPI.resumeAllTorrents();
        } catch (IOException e) {
            e.printStackTrace();
        }

        kodiAPI.closeConnection();

        return SUCCESS;
    }

    public String getqBittorrentUrl() {
        return qBittorrentUrl;
    }

    public void setqBittorrentUrl(String qBittorrentUrl) {
        this.qBittorrentUrl = qBittorrentUrl;
    }

    public String getTvstRemember() {
        return tvstRemember;
    }

    public void setTvstRemember(String tvstRemember) {
        this.tvstRemember = tvstRemember;
    }
}
