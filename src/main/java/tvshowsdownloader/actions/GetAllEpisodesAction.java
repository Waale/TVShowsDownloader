package tvshowsdownloader.actions;

import tvshowsdownloader.beans.Show;
import tvshowsdownloader.services.DownloadService;

import java.util.List;

/**
 * Created by Romain on 16/12/2018.
 */
public class GetAllEpisodesAction extends APIAction {

    private List<Show> shows;

    @Override
    public String execute() {
        try {
            setShows(new DownloadService().getAllEpisodes());
        } catch (Exception e) {
            return returnError(e);
        }

        return SUCCESS;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
