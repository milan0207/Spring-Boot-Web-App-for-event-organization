package edu.bbte.idde.kmim2248.dao.factories;


import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventJdbcDaoImpl;

public class JdbcDaoFactory extends DaoFactory {
    @Override
    public EventDao getEventDAO() {
        return new EventJdbcDaoImpl();
    }
}
