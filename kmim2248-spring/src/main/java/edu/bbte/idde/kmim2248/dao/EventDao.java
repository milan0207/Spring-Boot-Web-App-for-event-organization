package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventDao {
    Event save(Event event) throws DaoOperationException, EventNotFoundException;

    void deleteById(Long id) throws EventNotFoundException, DaoOperationException;

    Optional<Event> findByName(String eventName) throws DaoOperationException;

    boolean existsByName(String name) throws DaoOperationException;

    boolean existsById(Long id) throws DaoOperationException;

    List<Event> findAll() throws DaoOperationException;

    Optional<Event> findById(Long id) throws EventNotFoundException, DaoOperationException;

    Collection<Event> findByNameContainingIgnoreCase(String keyword) throws DaoOperationException;
}