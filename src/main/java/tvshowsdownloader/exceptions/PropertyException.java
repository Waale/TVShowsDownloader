package tvshowsdownloader.exceptions;

/**
 * Created by Romain on 15/12/2018.
 */
public class PropertyException extends Exception {
    private String name;

    private String value;

    public PropertyException(String name, String value) {
        super("Incorrect value for property \"" + name + "\" : " + value);
        this.name = name;
        this.value = value;
    }
}
