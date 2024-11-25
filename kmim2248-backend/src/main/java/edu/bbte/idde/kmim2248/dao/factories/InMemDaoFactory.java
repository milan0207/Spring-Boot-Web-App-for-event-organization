package edu.bbte.idde.kmim2248.dao.factories;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventInMemDaoImpl;

public class InMemDaoFactory extends DaoFactory {

    private static EventInMemDaoImpl eventInMemDaoImpl;

    @Override
    public EventDao getEventDAO() {
        if (eventInMemDaoImpl != null) {
            return eventInMemDaoImpl;
        }

        eventInMemDaoImpl = new EventInMemDaoImpl();
        return eventInMemDaoImpl;
    }
}
