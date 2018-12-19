package tvshowsdownloader.exceptions;

/**
 * Created by Romain on 15/12/2018.
 */
public class PropertyException extends Exception {
    private String key;

    private String value;

    public PropertyException(String key) {
        super("No value for property : " + key);
        this.key = key;
        this.value = value;
    }

    public PropertyException(String key, String value) {
        super("Incorrect value for property \"" + key + "\" : " + value);
        this.key = key;
        this.value = value;
    }
}
