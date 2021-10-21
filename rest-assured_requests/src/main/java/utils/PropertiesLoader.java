package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static PropertiesLoader appProperties = new PropertiesLoader("application.properties");

    Properties propertiesFile;

    public PropertiesLoader(String propertiesFileName) {
        propertiesFile = new Properties();
        InputStream propsFileStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
        try {
            propertiesFile.load(propsFileStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getPropertyValue(String propName) {
        return propertiesFile.getProperty(propName);
    }
}
