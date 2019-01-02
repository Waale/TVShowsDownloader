package tvshowsdownloader.actions;

import tvshowsdownloader.beans.Show;
import tvshowsdownloader.exceptions.ParameterException;
import tvshowsdownloader.services.DownloadService;

/**
 * Created by Romain on 16/12/2018.
 */
public class GetShowEpisodesAction extends APIAction {

    private Integer id;

    private String name;

    private Show show;

    @Override
    public String execute() {
        try {
            if (id != null) {
                setShow(new DownloadService().getShowEpisodes(id));
            } else if (name != null) {
                setShow(new DownloadService().getShowEpisodes(name));
            } else {
                throw new ParameterException("id");
            }
        } catch (Exception e) {
            return returnError(e);
        }

        return SUCCESS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
