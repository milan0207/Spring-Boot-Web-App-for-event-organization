package edu.bbte.idde.kmim2248.dao.factories;

import edu.bbte.idde.kmim2248.config.AppConfig;
import edu.bbte.idde.kmim2248.config.ConfigLoader;
import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventJdbcDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public abstract class DaoFactory {
    private static final Logger logger = LoggerFactory.getLogger(EventJdbcDaoImpl.class);

    private static final String daoType;

    public abstract EventDao getEventDAO();

    static {
        AppConfig config = null;
        try {
            config = ConfigLoader.loadConfig();
        } catch (Exception e) {
            logger.error("Failed to load application configuration", e);
        }
        assert config != null;
        daoType = config.getDatabase().getType();
    }

    public static DaoFactory getDAOFactory() {
        return switch (daoType) {
            case "jdbc" -> new JdbcDaoFactory();
            case "inmemory" -> new InMemDaoFactory();
            default -> throw new IllegalArgumentException("Unsupported DAO type: " + daoType);
        };
    }
}

