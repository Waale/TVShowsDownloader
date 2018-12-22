package tvshowsdownloader.actions;

import tvshowsdownloader.beans.Show;
import tvshowsdownloader.services.DownloadService;

/**
 * Created by Romain on 16/12/2018.
 */
public class GetShowEpisodesAction extends APIAction {

    private String name;

    private Show show;

    @Override
    public String execute() {
        try {
            setShow(new DownloadService().getShowEpisodes(name));
        } catch (Exception e) {
            return returnError(e);
        }

        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
