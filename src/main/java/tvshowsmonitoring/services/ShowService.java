package tvshowsmonitoring.services;

import java.util.HashMap;
import java.util.Map;

import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;
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
}
