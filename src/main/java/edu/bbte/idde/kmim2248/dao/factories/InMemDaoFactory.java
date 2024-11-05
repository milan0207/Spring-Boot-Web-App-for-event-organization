package edu.bbte.idde.kmim2248.dao.factories;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventInMemDaoImpl;
public class InMemDaoFactory extends DaoFactory{
    @Override
    public EventDao getEventDAO() {
        return new EventInMemDaoImpl();
    }
}
