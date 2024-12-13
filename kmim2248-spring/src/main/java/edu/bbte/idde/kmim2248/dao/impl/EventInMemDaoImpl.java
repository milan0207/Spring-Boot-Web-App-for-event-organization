package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class EventInMemDaoImpl implements EventDao {
    private static final Logger logger = LoggerFactory.getLogger(EventInMemDaoImpl.class);
    private final Map<Long, Events> eventMap = new ConcurrentHashMap<>();
    Long id;

    public EventInMemDaoImpl() {
        id = 0L;
    }

    @Override
    public void save(Events event) {
        event.setId(id);
        eventMap.put(id, event);
        id++;
        logger.info("Events saved: {}", event);
    }

    @Override
    public void update(Events event) throws EventNotFoundException {
        if (!eventMap.containsKey(event.getId())) {
            throw new EventNotFoundException("Events with ID: " + event.getId() + " not found.");
        }
        eventMap.put(event.getId(), event);
        logger.info("Events updated: {}", event);
    }

    @Override
    public void delete(Long id) throws EventNotFoundException {
        if (!eventMap.containsKey(id)) {
            throw new EventNotFoundException("Events with ID: " + id + " not found.");
        }
        logger.info("Events deleted: {}", id);
        eventMap.remove(id);
    }

    @Override
    public Events findByName(String eventName) throws EventNotFoundException {
        for (Events event : eventMap.values()) {
            if (event.getName().equals(eventName)) {
                logger.info("Events found by name: {}", eventName);
                return event;
            }
        }
        throw new EventNotFoundException("Events not found" + eventName);
    }

    @Override
    public boolean existsByName(String eventName) {
        for (Events event : eventMap.values()) {
            if (event.getName().equals(eventName)) {
                logger.info("Events exists by name: {}", eventName);
                return true;
            }
        }
        logger.info("Events does not exist by name: {}", eventName);
        return false;
    }

    @Override
    public Map<Long, Events> getAllEvents() {
        logger.info("All events returned from memory");
        return eventMap;
    }

    @Override
    public Events findById(Long id) throws EventNotFoundException {
        if (!eventMap.containsKey(id)) {
            throw new EventNotFoundException("Events with ID: " + id + " not found.");
        }
        logger.info("Events found by ID: {}", id);
        return eventMap.get(id);
    }

    @Override
    public Collection<Events> findByNameContainingIgnoreCase(String keyword) {
        return eventMap.values()
                .stream()
                .filter(event -> event.getName().toLowerCase(Locale.ROOT)
                        .startsWith(keyword.toLowerCase(Locale.ROOT))).toList();
    }

    @Override
    public boolean existsById(Long id) {
        if (eventMap.containsKey(id)) {
            logger.info("Events exists by ID: {}", id);
            return true;
        }
        logger.info("Events does not exist by ID: {}", id);
        return false;
    }

}