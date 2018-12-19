package tvshowsdownloader.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.services.DownloadService;

import java.util.List;

/**
 * Created by Romain on 16/12/2018.
 */
public class GetEpisodesAction extends Action {
    private List<Show> data;

    private String jsonStringResult;

    @Override
    public String execute() {
        try {
            jsonStringResult = new ObjectMapper().writeValueAsString(new DownloadService().getEpisodesToDownload());
        } catch (Exception e) {
            addActionError(e.getMessage());
            return getErrorCode(e);
        }

        return SUCCESS;
    }

    public List<Show> getData() {
        return data;
    }

    public void setData(List<Show> data) {
        this.data = data;
    }

    public String getJsonStringResult() {
        return jsonStringResult;
    }

    public void setJsonStringResult(String jsonStringResult) {
        this.jsonStringResult = jsonStringResult;
    }

}
