package tvshowsmonitoring.services;

import java.util.Map;

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
    	TVTimeShow show = tvTimeShow;
    	show.setSeasons(null);
    	
    	for (Map.Entry<Integer, TVTimeSeason> seasonEntry : tvTimeShow.getSeasons().entrySet()) {
    		TVTimeSeason unownedSeason = new TVTimeSeason(seasonEntry.getKey());
    		for (Map.Entry<Integer, TVTimeEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
    			if (!kodiShow.hasEpisode(seasonEntry.getKey(), episodeEntry.getKey())) {
    				unownedSeason.addEpisode(episodeEntry.getKey(), episodeEntry.getValue());
    			}
    		}
    		
    		if (!unownedSeason.hasEpisodes()) {
    			show.addSeason(seasonEntry.getKey(), unownedSeason);
    		}
    	}
    	
    	return show;
    }
}
