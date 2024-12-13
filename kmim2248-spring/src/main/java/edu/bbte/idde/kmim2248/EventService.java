package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Events;
import edu.bbte.idde.kmim2248.model.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {


    private final EventDao eventDao;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventDao eventDao, EventMapper eventMapper) {
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    public List<EventDTO> getAllEvents() throws DaoOperationException {
        return eventDao.getAllEvents()
                .values()
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    public EventDTO getEventById(Long id) throws EventNotFoundException, DaoOperationException {
        Events events = eventDao.findById(id);
        return eventMapper.toDTO(events);
    }

    public EventDTO createEvent(EventDTO eventDTO) throws DaoOperationException {
        Events events = eventMapper.toEvent(eventDTO);
        eventDao.save(events);
        return eventMapper.toDTO(events);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) throws DaoOperationException, EventNotFoundException {
        eventDTO.setId(id);
        Events existingEvent = eventDao.findById(id);
        eventDao.update(eventMapper.toEvent(eventDTO));
        return eventMapper.toDTO(existingEvent);
    }

    public void deleteEvent(Long id) throws DaoOperationException, EventNotFoundException {
        eventDao.delete(id);
    }

    public List<EventDTO> searchEntities(String keyword) throws DaoOperationException {
        return eventDao.findByNameContainingIgnoreCase(keyword).stream()
                .map(eventMapper::toDTO)
                .toList();
    }
}

