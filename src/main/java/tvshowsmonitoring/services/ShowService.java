package tvshowsmonitoring.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;
import qbittorrentapi.beans.QBitTorrent;
import qbittorrentapi.beans.QBitTorrentList;
import qbittorrentapi.services.QBitAPI;
import tvtimeapi.beans.TVTimeEpisode;
import tvtimeapi.beans.TVTimeSeason;
import tvtimeapi.beans.TVTimeShow;

/**
 * Created by Romain on 20/11/2017.
 */
public class ShowService {
    public Boolean allEpisodesAreInLibrary(KodiShow kodiShow, TVTimeShow tvTimeShow) {
        return kodiShow.getNumberOfUnwatchedEpisodes() == tvTimeShow.getRemainingEpisodes();
    }
    
    public TVTimeShow getUnownedEpisodes(KodiShow kodiShow, TVTimeShow tvTimeShow) {
    	Map<Integer, TVTimeSeason> unownedSeasons = new HashMap<Integer, TVTimeSeason>();
    	
    	for (Map.Entry<Integer, TVTimeSeason> seasonEntry : tvTimeShow.getSeasons().entrySet()) {
    		TVTimeSeason unownedSeason = new TVTimeSeason(seasonEntry.getKey());
    		for (Map.Entry<Integer, TVTimeEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
    			if (!kodiShow.hasEpisode(seasonEntry.getKey(), episodeEntry.getKey())) {
    				unownedSeason.addEpisode(episodeEntry.getKey(), episodeEntry.getValue());
    			}
    		}
    		
    		if (unownedSeason.hasEpisodes()) {
				unownedSeasons.put(seasonEntry.getKey(), unownedSeason);
    		}
    	}

        tvTimeShow.setSeasons(unownedSeasons);
    	return tvTimeShow;
    }

    public TVTimeShow getNotDownloadingEpisodes(QBitTorrentList qBitTorrentList, TVTimeShow tvTimeShow) {
    	String tvShowName = tvTimeShow.getName();
    	Map<Integer, TVTimeSeason> seasons = new HashMap<Integer, TVTimeSeason>();

    	for (Map.Entry<Integer, TVTimeSeason> tvTimeSeasonEntry : tvTimeShow.getSeasons().entrySet()) {
    		TVTimeSeason tvTimeSeason = tvTimeSeasonEntry.getValue();
			Map<Integer, TVTimeEpisode> episodes = getNotDownloadingEpisodes(qBitTorrentList, tvShowName, tvTimeSeason);
			if (!episodes.isEmpty()) {
    			tvTimeSeason.setEpisodes(episodes);
    			seasons.put(tvTimeSeasonEntry.getKey(), tvTimeSeason);
			}
		}

		tvTimeShow.setSeasons(seasons);

    	return tvTimeShow;
	}

	private Map<Integer, TVTimeEpisode> getNotDownloadingEpisodes(QBitTorrentList qBitTorrentList, String tvShowName, TVTimeSeason tvTimeSeason) {
		Map<Integer, TVTimeEpisode> episodes = new HashMap<Integer, TVTimeEpisode>();
		episodes.putAll(tvTimeSeason.getEpisodes());
		for (Map.Entry<Integer, TVTimeEpisode> tvTimeEpisodeEntry : tvTimeSeason.getEpisodes().entrySet()) {
            TVTimeEpisode tvTimeEpisode = tvTimeEpisodeEntry.getValue();
			for (QBitTorrent qBitTorrent : qBitTorrentList) {
				String regex = tvShowName +  " " + tvTimeSeason.getDownloadLinkPart() + "(E\\d+-)?" + tvTimeEpisode.getDownloadLinkPart();
				regex = regex.replace(".", "\\.");
				if (qBitTorrent.getName().matches(regex)) {
					episodes.remove(tvTimeEpisodeEntry.getKey());
					break;
				}
			}
        }
		return episodes;
	}

	public void downloadEpisodes(TVTimeShow tvTimeShow) {
    	// TODO Search episodes on ThePirateBay and download on qBittorrent
	}
}
