package tvshowsdownloader.exceptions;

/**
 * Created by Romain on 15/12/2018.
 */
public class ParameterException extends Exception {
    private String key;

    private String value;

    public ParameterException(String key) {
        super("No value for parameter : " + key);
        this.key = key;
        this.value = value;
    }

    public ParameterException(String key, String value) {
        super("Incorrect value for parameter \"" + key + "\" : " + value);
        this.key = key;
        this.value = value;
    }
}
