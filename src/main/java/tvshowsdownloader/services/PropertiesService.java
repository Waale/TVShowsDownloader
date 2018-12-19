package tvshowsdownloader.services;

import tvshowsdownloader.exceptions.PropertyException;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Romain on 16/12/2018.
 */
public class PropertiesService {
    Properties properties;

    public PropertiesService() throws Exception {
        Context context = new InitialContext();
        String configurationPath = (String) context.lookup("java:comp/env/tvShowsDownloaderConfigurationPath");
        InputStream is = new FileInputStream(configurationPath);

        properties = new Properties();
        properties.load(is);
    }

    public String getProperty(String key) throws PropertyException {
        String property = properties.getProperty(key);

        if (property == null) {
            throw new PropertyException(key);
        }

        return property;
    }
}
