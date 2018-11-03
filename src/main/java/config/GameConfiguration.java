package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfiguration {
    private final InputStream inputStream;

    public GameConfiguration(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Properties getGameConfiguration() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        inputStream.close();

        return prop;
    }
}
