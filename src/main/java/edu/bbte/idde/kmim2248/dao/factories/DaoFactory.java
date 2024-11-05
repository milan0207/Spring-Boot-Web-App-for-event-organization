package edu.bbte.idde.kmim2248.dao.factories;

import edu.bbte.idde.kmim2248.dao.EventDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class DaoFactory {

    public abstract EventDao getEventDAO();

    private static final String CONFIG_FILE = "/dao-config.properties";
    private static String daoType;

    static {
        try (InputStream input = DaoFactory.class.getResourceAsStream(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(input);
            daoType = properties.getProperty("daoType", "jdbc"); // Default to "jdbc" if not specified
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DAO configuration", e);
        }
    }

    public static DaoFactory getDAOFactory() {
        switch (daoType) {
            case "jdbc":
                return new JdbcDaoFactory();
            case "inmemory":
                return new InMemDaoFactory();
            default:
                throw new IllegalArgumentException("Unsupported DAO type: " + daoType);
        }
    }
}

