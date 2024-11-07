package edu.bbte.idde.kmim2248.dao.factories;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventJdbcDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class DaoFactory {
    private static final Logger logger = LoggerFactory.getLogger(EventJdbcDaoImpl.class);

    private static final String CONFIG_FILE = "/dao-config.properties";
    private static String daoType;

    public abstract EventDao getEventDAO();

    static {
        try (InputStream input = DaoFactory.class.getResourceAsStream(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(input);
            daoType = properties.getProperty("daoType", "jdbc"); // Default to "jdbc" if not specified
        } catch (IOException e) {
            logger.error("Failed to load DAO configuration file", e);
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

