package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;

import java.util.Map;

public class EventService {
    private final EventDao eventDao;

    public EventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void createEvent(Event event) {
        eventDao.save(event);
    }

    public void updateEvent(Event event) throws EventNotFoundException {
        eventDao.update(event);
    }

    public void deleteEvent(String eventName) throws EventNotFoundException {
        eventDao.delete(eventName);
    }


    public Map<String, Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    public Event findEventByName(String name) throws EventNotFoundException {
        return eventDao.findByName(name).orElse(null);
    }
}