package edu.bbte.idde.kmim2248.dao.factories;

import edu.bbte.idde.kmim2248.dao.EventDao;

public abstract class DaoFactory {
    public abstract EventDao getEventDAO();

    public static DaoFactory getDAOFactory(String type) {
        switch (type) {
            case "mysql":
                return new JdbcDaoFactory();
            case "inmemory":
                return new InMemDaoFactory();
            default:
                throw new IllegalArgumentException("Invalid DAOFactory type");
        }
    }
}
