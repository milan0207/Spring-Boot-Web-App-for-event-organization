package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;

import java.util.Map;
import java.util.Optional;

public interface EventDao {
    void save(Event event);
    void update(Event event) throws EventNotFoundException;
    void delete(String eventName) throws EventNotFoundException;
    Optional<Event> findByName(String eventName);
    Map<String, Event> getAllEvents();
}