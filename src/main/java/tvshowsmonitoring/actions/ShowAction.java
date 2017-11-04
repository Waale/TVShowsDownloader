package tvshowsmonitoring.actions;

import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.services.TVTimeAPI;

/**
 * Created by Romain on 04/11/2017.
 */
public class ShowAction extends Action {
    TVTimeShow show;

    String id;

    String name;

    String episode;

    Boolean remainingEpisodes;

    String poster;

    String link;

    String tvstRemember;

    public String execute() {
        TVTimeAPI tvTimeAPI = new TVTimeAPI();

        show = tvTimeAPI.getShow(id, name, episode, remainingEpisodes, poster, link, tvstRemember);

        return SUCCESS;
    }

    public TVTimeShow getShow() {
        return show;
    }

    public void setShow(TVTimeShow show) {
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Boolean getRemainingEpisodes() {
        return remainingEpisodes;
    }

    public void setRemainingEpisodes(Boolean remainingEpisodes) {
        this.remainingEpisodes = remainingEpisodes;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTvstRemember() {
        return tvstRemember;
    }

    public void setTvstRemember(String tvstRemember) {
        this.tvstRemember = tvstRemember;
    }
}
