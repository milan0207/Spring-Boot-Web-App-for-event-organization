package edu.bbte.idde.kmim2248.dao.factories;


import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventJdbcDaoImpl;

public class JdbcDaoFactory extends DaoFactory {

    private static volatile EventJdbcDaoImpl eventJdbcDaoImpl;

    @Override
    public EventDao getEventDAO() {

        if (eventJdbcDaoImpl != null) {
            return eventJdbcDaoImpl;
        }

        eventJdbcDaoImpl = new EventJdbcDaoImpl();
        return eventJdbcDaoImpl;
    }
}
