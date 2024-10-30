package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;

import java.util.*;

public class InMemDaoImpl implements EventDao {
    private final Map<String, Event> eventMap = new HashMap<>();

    @Override
    public void save(Event event)  {
        eventMap.put(event.getName(), event);
    }

    @Override
    public void update(Event event) throws EventNotFoundException {
        if (!eventMap.containsKey(event.getName())) {
            throw new EventNotFoundException("Event with name " + event.getName() + " not found.");
        }
        eventMap.put(event.getName(), event);
    }

    @Override
    public void delete(String eventName) throws EventNotFoundException {
        if (!eventMap.containsKey(eventName)) {
            throw new EventNotFoundException("Event with name " + eventName + " not found.");
        }
        eventMap.remove(eventName);
    }

    @Override
    public Optional<Event> findByName(String eventName) throws EventNotFoundException {
        if(!eventMap.containsKey(eventName)) {
            throw new EventNotFoundException("Event with name " + eventName + " not found.");
        }
        return Optional.ofNullable(eventMap.get(eventName));
    }

    public Map<String, Event> getAllEvents() {
        return eventMap;
    }
}