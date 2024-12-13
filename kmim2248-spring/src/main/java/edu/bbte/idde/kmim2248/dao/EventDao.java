package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Events;

import java.util.Collection;
import java.util.Map;

public interface EventDao {
    void save(Events event) throws DaoOperationException;

    void update(Events event) throws EventNotFoundException, DaoOperationException;

    void delete(Long id) throws EventNotFoundException, DaoOperationException;

    Events findByName(String eventName) throws EventNotFoundException, DaoOperationException;

    boolean existsByName(String eventName) throws DaoOperationException;

    boolean existsById(Long id) throws DaoOperationException;

    Map<Long, Events> getAllEvents() throws DaoOperationException;

    Events findById(Long id) throws EventNotFoundException, DaoOperationException;

    Collection<Events> findByNameContainingIgnoreCase(String keyword) throws DaoOperationException;
}