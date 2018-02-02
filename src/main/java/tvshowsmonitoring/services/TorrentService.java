package tvshowsmonitoring.services;

import qbittorrentapi.services.QBitAPI;
import tvtimeapi.beans.TVTimeEpisode;

/**
 * Created by Romain on 02/02/2018.
 */
public class TorrentService {
    public void downloadEpisode(QBitAPI qBitAPI, TVTimeEpisode episode) {
        // TODO Search episode on ThePirateBay and download on qBittorrent
    }

    public void removeDownloadedEpisodes(QBitAPI qBitAPI) {
        // TODO Remove downloaded episodes from qBittorrent
    }
}
