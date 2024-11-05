package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventAlreadyExistsException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;

import java.util.Map;
import java.util.Optional;

public interface EventDao {
    void save(Event event) throws DaoOperationException;

    void update(Event event) throws EventNotFoundException, DaoOperationException;

    void delete(String eventName) throws EventNotFoundException, DaoOperationException;

    Optional<Event> findByName(String eventName) throws EventNotFoundException, DaoOperationException;

    boolean existsByName(String eventName) throws DaoOperationException;

    Map<String, Event> getAllEvents() throws DaoOperationException;
}