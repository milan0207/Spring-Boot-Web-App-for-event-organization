package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventInMemDaoImpl implements EventDao {
    private static final Logger logger = LoggerFactory.getLogger(EventJdbcDaoImpl.class);
    private final Map<Integer, Event> eventMap = new ConcurrentHashMap<>();
    int id;

    public EventInMemDaoImpl() {
        id = 0;
    }

    @Override
    public void save(Event event) {
        event.setId(id);
        eventMap.put(id, event);
        id++;
        logger.info("Event saved: {}", event);
    }

    @Override
    public void update(Event event) throws EventNotFoundException {
        if (!eventMap.containsKey(event.getId())) {
            throw new EventNotFoundException("Event with ID: " + event.getId() + " not found.");
        }
        eventMap.put(event.getId(), event);
        logger.info("Event updated: {}", event);
    }

    @Override
    public void delete(int id) throws EventNotFoundException {
        if (!eventMap.containsKey(id)) {
            throw new EventNotFoundException("Event with ID: " + id + " not found.");
        }
        logger.info("Event deleted: {}", id);
        eventMap.remove(id);
    }

    @Override
    public Event findByName(String eventName) throws EventNotFoundException {
        for (Event event : eventMap.values()) {
            if (event.getName().equals(eventName)) {
                logger.info("Event found by name: {}", eventName);
                return event;
            }
        }
        throw new EventNotFoundException("Event not found" + eventName);
    }

    @Override
    public boolean existsByName(String eventName) {
        for (Event event : eventMap.values()) {
            if (event.getName().equals(eventName)) {
                logger.info("Event exists by name: {}", eventName);
                return true;
            }
        }
        logger.info("Event does not exist by name: {}", eventName);
        return false;
    }

    @Override
    public Map<Integer, Event> getAllEvents() {
        logger.info("All events returned");
        return eventMap;
    }

    @Override
    public Event findById(int id) throws EventNotFoundException {
        if (!eventMap.containsKey(id)) {
            throw new EventNotFoundException("Event with ID: " + id + " not found.");
        }
        logger.info("Event found by ID: {}", id);
        return eventMap.get(id);
    }

    @Override
    public boolean existsById(int id) {
        if (eventMap.containsKey(id)) {
            logger.info("Event exists by ID: {}", id);
            return true;
        }
        logger.info("Event does not exist by ID: {}", id);
        return false;
    }

}