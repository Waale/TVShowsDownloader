package tvshowsdownloader.beans;

import qbittorrentapi.beans.QBitTorrent;
import synologydownloadstationapi.beans.SynoDSTorrent;

/**
 * Created by Romain on 15/12/2018.
 */
public class Torrent {
    private String name;

    public Torrent() {}

    public Torrent(QBitTorrent qBitTorrent) {
        this.name = qBitTorrent.getName();
    }

    public Torrent(SynoDSTorrent synoDSTorrent) {
        this.name = synoDSTorrent.getTitle();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
