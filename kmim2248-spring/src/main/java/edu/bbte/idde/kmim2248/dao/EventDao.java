package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;

import java.util.Collection;
import java.util.Map;

public interface EventDao {
    void save(Event event) throws DaoOperationException;

    void update(Event event) throws EventNotFoundException, DaoOperationException;

    void delete(Long id) throws EventNotFoundException, DaoOperationException;

    Event findByName(String eventName) throws EventNotFoundException, DaoOperationException;

    boolean existsByName(String eventName) throws DaoOperationException;

    boolean existsById(Long id) throws DaoOperationException;

    Map<Long, Event> getAllEvents() throws DaoOperationException;

    Event findById(Long id) throws EventNotFoundException, DaoOperationException;

    Collection<Event> findByNameContainingIgnoreCase(String keyword) throws DaoOperationException;
}