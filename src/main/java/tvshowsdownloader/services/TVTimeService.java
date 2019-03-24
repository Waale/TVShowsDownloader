package tvshowsdownloader.services;

import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvtimeapi.beans.TVTimeEpisode;
import tvtimeapi.beans.TVTimeShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romain on 22/12/2018.
 */
public class TVTimeService {
    public static List<Show> parseShows(List<TVTimeShow> tvTimeShows) throws Exception {
        List<Show> shows = new ArrayList<>();

        for (TVTimeShow tvTimeShow : tvTimeShows) {
            shows.add(parseShow(tvTimeShow));
        }

        return shows;
    }

    public static Show parseShow(TVTimeShow tvTimeShow) {
        Show show = new Show();
        show.setId(tvTimeShow.getId());
        show.setName(tvTimeShow.getName());
        show.setOverview(tvTimeShow.getOverview());
        show.setBanner(tvTimeShow.getBanner());
        show.setPoster(tvTimeShow.getPoster());
        show.setAiredEpisodes(tvTimeShow.getAiredEpisodes());
        show.setSeenEpisodes(tvTimeShow.getSeenEpisodes());

        if (tvTimeShow.getEpisodes() != null) {
            List<Episode> episodes = new ArrayList<>();
            for (TVTimeEpisode episode : tvTimeShow.getEpisodes()) {
                episodes.add(new Episode(episode.getSeason(), episode.getNumber(), episode.getName(), episode.getWatched()));
            }
            show.setEpisodes(episodes);

            TVTimeEpisode nextEpisode = tvTimeShow.getNextEpisode();
            if (nextEpisode != null) {
                show.setNextEpisode(new Episode(nextEpisode.getSeason(), nextEpisode.getNumber(), nextEpisode.getName(), nextEpisode.getWatched()));
            } else if (!episodes.isEmpty()) {
                show.setNextEpisode(episodes.get(0));
            }
        }

        return show;
    }
}
