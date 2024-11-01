package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class InMemDaoImpl implements EventDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoImpl.class);
    private final Map<String, Event> eventMap = new HashMap<>();

    @Override
    public void save(Event event)  {
        eventMap.put(event.getName(), event);
        logger.info("Event saved: {}", event);
    }

    @Override
    public void update(Event event) throws EventNotFoundException {
        if (!eventMap.containsKey(event.getName())) {
            throw new EventNotFoundException("Event with name " + event.getName() + " not found.");
        }
        eventMap.put(event.getName(), event);
        logger.info("Event updated: {}", event);
    }

    @Override
    public void delete(String eventName) throws EventNotFoundException {
        if (!eventMap.containsKey(eventName)) {
            throw new EventNotFoundException("Event with name " + eventName + " not found.");
        }
        logger.info("Event deleted: {}", eventName);
        eventMap.remove(eventName);
    }

    @Override
    public Optional<Event> findByName(String eventName) throws EventNotFoundException {
        if(!eventMap.containsKey(eventName)) {
            throw new EventNotFoundException("Event with name " + eventName + " not found.");
        }
        logger.info("Event found: {}", eventName);
        return Optional.ofNullable(eventMap.get(eventName));
    }

    public Map<String, Event> getAllEvents() {
        logger.info("All events returned");
        return eventMap;
    }
}