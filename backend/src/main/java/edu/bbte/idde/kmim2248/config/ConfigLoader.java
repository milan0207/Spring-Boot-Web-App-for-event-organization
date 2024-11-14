package edu.bbte.idde.kmim2248.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.kmim2248.dao.exception.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    private static AppConfig config;

    public static synchronized AppConfig loadConfig() throws ConfigurationException, IOException {
        if (config != null) {
            return config;
        }

        String profile = System.getenv("APP_PROFILE");
        if (profile == null) {
            profile = System.getProperty("app.profile", "dev");
        }

        String configFile = String.format("/application-%s.json", profile);
        InputStream is = ConfigLoader.class.getResourceAsStream(configFile);

        if (is == null) {
            throw new ConfigurationException("Configuration file not found: " + configFile, null);
        }

        ObjectMapper mapper = new ObjectMapper();
        config = mapper.readValue(is, AppConfig.class);
        return config;
    }
}

/*

set APP_PROFILE=prod
echo %APP_PROFILE%
* */