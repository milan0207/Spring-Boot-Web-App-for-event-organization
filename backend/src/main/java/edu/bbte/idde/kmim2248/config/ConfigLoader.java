package edu.bbte.idde.kmim2248.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

public class ConfigLoader {
    private static AppConfig config;

    public static AppConfig loadConfig() throws Exception {
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
            throw new RuntimeException("Configuration file not found: " + configFile);
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