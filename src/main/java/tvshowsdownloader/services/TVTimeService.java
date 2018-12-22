package tvshowsdownloader.services;

import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvtimeapi.beans.TVTimeEpisode;
import tvtimeapi.beans.TVTimeSeason;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.beans.TVTimeWatchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Romain on 22/12/2018.
 */
public class TVTimeService {
    public static List<Show> parseWatchlist(TVTimeWatchlist watchlist) throws Exception {
        List<Show> shows = new ArrayList<>();

        for (TVTimeShow tvTimeShow : watchlist) {
            shows.add(parseShow(tvTimeShow));
        }

        return shows;
    }

    public static Show parseShow(TVTimeShow tvTimeShow) {
        Show show = new Show();
        show.setName(tvTimeShow.getName());
        show.setBanner(tvTimeShow.getBanner());

        List<Episode> episodes = new ArrayList<>();
        for (Map.Entry<Integer, TVTimeSeason> seasonEntry : tvTimeShow.getSeasons().entrySet()) {
            for (Map.Entry<Integer, TVTimeEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
                episodes.add(new Episode(seasonEntry.getKey(), episodeEntry.getKey(), episodeEntry.getValue().getTitle()));
            }
        }
        show.setEpisodes(episodes);

        return show;
    }
}
