package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventAlreadyExistsException;
import edu.bbte.idde.kmim2248.dao.factories.DaoFactory;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;

import java.util.Map;

public class EventService {
    private final EventDao eventDao;

    public EventService(String dbType) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory();
        this.eventDao = daoFactory.getEventDAO();
    }

    public void createEvent(Event event) throws DaoOperationException, EventAlreadyExistsException {
       if(eventDao.existsByName(event.getName())) {
           throw new EventAlreadyExistsException("Event with name " + event.getName() + " already exists.");
       }
         eventDao.save(event);
    }

    public void updateEvent(Event event) throws EventNotFoundException, DaoOperationException{
        eventDao.update(event);
    }

    public void deleteEvent(String eventName) throws EventNotFoundException, DaoOperationException{
        eventDao.delete(eventName);
    }


    public Map<String, Event> getAllEvents() throws DaoOperationException{
        return eventDao.getAllEvents();
    }

    public Event findEventByName(String name) throws EventNotFoundException, DaoOperationException{
        return eventDao.findByName(name).orElse(null);
    }
}