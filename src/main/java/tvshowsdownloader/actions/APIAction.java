package tvshowsdownloader.actions;

import com.opensymphony.xwork2.ActionSupport;
import qbittorrentapi.exceptions.QBitLoginException;
import qbittorrentapi.exceptions.QBitParametersException;
import qbittorrentapi.exceptions.QBitURLException;
import synologydownloadstationapi.exceptions.SynoDSLoginException;
import synologydownloadstationapi.exceptions.SynoDSParameterException;
import synologydownloadstationapi.exceptions.SynoDSURLException;
import thepiratebayapi.exceptions.TPBayParametersException;
import thepiratebayapi.exceptions.TPBayURLException;
import tvshowsdownloader.exceptions.ParameterException;
import tvshowsdownloader.exceptions.PropertyException;
import tvtimeapi.exceptions.TVTimeLoginException;
import tvtimeapi.exceptions.TVTimeURLException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Romain on 16/12/2018.
 */
public abstract class APIAction extends ActionSupport {

    public static final String BAD_REQUEST = "bad-request-error";

    public static final String UNAUTHORIZED = "unauthorized-error";

    public static final String NOT_FOUND = "not-found-error";

    protected Map<Class<? extends Exception>, String> errorMap = new HashMap<>();

    public APIAction() {
        initErrorMap();
    }

    private void initErrorMap() {
        // 400
        errorMap.put(PropertyException.class, BAD_REQUEST);
        errorMap.put(ParameterException.class, BAD_REQUEST);
        errorMap.put(QBitParametersException.class, BAD_REQUEST);
        errorMap.put(TPBayParametersException.class, BAD_REQUEST);
        errorMap.put(SynoDSParameterException.class, BAD_REQUEST);

        // 401
        errorMap.put(TVTimeLoginException.class, UNAUTHORIZED);
        errorMap.put(QBitLoginException.class, UNAUTHORIZED);
        errorMap.put(SynoDSLoginException.class, UNAUTHORIZED);

        // 404
        errorMap.put(FileNotFoundException.class, NOT_FOUND);
        errorMap.put(TVTimeURLException.class, NOT_FOUND);
        errorMap.put(TPBayURLException.class, NOT_FOUND);
        errorMap.put(QBitURLException.class, NOT_FOUND);
        errorMap.put(SynoDSURLException.class, NOT_FOUND);
    }

    protected String returnError(Exception e) {
        addActionError(e.getMessage());

        String errorCode = errorMap.get(e.getClass());

        if (errorCode == null) {
            errorCode = ERROR;
        }

        return errorCode;
    }

    protected String returnDownloadErrors(List<String> messages) {
        for (String message : messages) {
            addActionError(message);
        }

        return ERROR;
    }
}
