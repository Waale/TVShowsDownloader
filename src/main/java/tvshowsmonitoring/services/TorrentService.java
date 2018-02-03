package tvshowsmonitoring.services;

import qbittorrentapi.beans.QBitTorrent;
import qbittorrentapi.services.QBitAPI;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.beans.TPBayTorrentResultList;
import thepiratebayapi.services.TPBayAPI;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romain on 02/02/2018.
 */
public class TorrentService {
    public void downloadEpisode(QBitAPI qBitAPI, TPBayAPI tpBayAPI, String showName, String episode) {
        if (showName.substring(showName.length() - 1).equals(".")) {
            showName = showName.substring(0, showName.length() - 1);
        }
        TPBayTorrentResultList tpBayTorrentResultList = tpBayAPI.searchTorrents(showName.replace("'", "") + " " + episode, new HashMap<String, Serializable>());
        if (tpBayTorrentResultList != null && !tpBayTorrentResultList.isEmpty()) {
            TPBayTorrent tpBayTorrent = tpBayAPI.getTorrent(tpBayTorrentResultList.get(0).getUrl());

            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("category", "Série");
            params.put("rename", showName + " " + episode);
            params.put("paused", true);
            params.put("savepath", "B:\\Videos\\Séries\\" + showName);
            try {
                qBitAPI.addTorrent(tpBayTorrent.getMagnet(), params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeDownloadedEpisodes(QBitAPI qBitAPI) {
        try {
            for (QBitTorrent qBitTorrent : qBitAPI.getTorrentList()) {
                if (qBitTorrent.getProgress() == 1) {
                    qBitAPI.deleteTorrent(qBitTorrent.getHash());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
