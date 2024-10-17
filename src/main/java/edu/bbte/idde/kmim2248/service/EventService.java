package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.exception.EventNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Event findEventByName(String name) {
        return eventDao.findByName(name).orElse(null);
    }
}