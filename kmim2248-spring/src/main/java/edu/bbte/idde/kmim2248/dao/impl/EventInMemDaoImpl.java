package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class EventInMemDaoImpl implements EventDao {
    private static final Logger logger = LoggerFactory.getLogger(EventInMemDaoImpl.class);
    private final Map<Long, Event> eventMap = new ConcurrentHashMap<>();
    Long id;

    public EventInMemDaoImpl() {
        id = 0L;
    }

    @Override
    public Event save(Event event) {
        event.setId(id);
        eventMap.put(id, event);
        id++;
        logger.info("Events saved: {}", event);
        return event;
    }

    @Override
    public void deleteById(Long id) throws EventNotFoundException {
        if (!eventMap.containsKey(id)) {
            throw new EventNotFoundException("Events with ID: " + id + " not found.");
        }
        logger.info("Events deleted: {}", id);
        eventMap.remove(id);
    }

    @Override
    public Optional<Event> findByName(String eventName) {
        for (Event event : eventMap.values()) {
            if (event.getName().equals(eventName)) {
                logger.info("Events found by name: {}", eventName);
                return Optional.of(event);
            }
        }
        logger.info("Events not found by name: {}", eventName);
        return Optional.empty();
    }

    @Override
    public boolean existsByName(String eventName) {
        for (Event event : eventMap.values()) {
            if (event.getName().equals(eventName)) {
                logger.info("Events exists by name: {}", eventName);
                return true;
            }
        }
        logger.info("Events does not exist by name: {}", eventName);
        return false;
    }

    @Override
    public List<Event> findAll() {
        logger.info("All events returned from memory");
        return new ArrayList<>(eventMap.values());
    }

    @Override
    public Optional<Event> findById(Long id) throws EventNotFoundException {
        if (!eventMap.containsKey(id)) {
            throw new EventNotFoundException("Events with ID: " + id + " not found.");
        }
        logger.info("Events found by ID: {}", id);
        return Optional.ofNullable(eventMap.get(id));
    }

    @Override
    public Collection<Event> findByNameContainingIgnoreCase(String keyword) {
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