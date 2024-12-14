package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
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

    public List<EventOutDTO> getAllEvents() throws DaoOperationException {
        return eventDao.getAllEvents()
                .values()
                .stream()
                .map(eventMapper::toEventOutDTO)
                .toList();
    }

    public EventOutDTO getEventById(Long id) throws EventNotFoundException, DaoOperationException {
        Event event = eventDao.findById(id);
        return eventMapper.toEventOutDTO(event);
    }

    public EventOutDTO createEvent(EventInDTO eventDTO) throws DaoOperationException {
        Event event = eventMapper.toEvent(eventDTO);
        eventDao.save(event);
        return eventMapper.toEventOutDTO(event);
    }

    public EventOutDTO updateEvent(Long id, EventInDTO eventDTO) throws DaoOperationException, EventNotFoundException {
        Event existingEvent = eventDao.findById(id);
        eventDao.update(eventMapper.toEvent(eventDTO));
        return eventMapper.toEventOutDTO(existingEvent);
    }

    public void deleteEvent(Long id) throws DaoOperationException, EventNotFoundException {
        eventDao.delete(id);
    }

    public List<EventOutDTO> searchEntities(String keyword) throws DaoOperationException {
        return eventDao.findByNameContainingIgnoreCase(keyword).stream()
                .map(eventMapper::toEventOutDTO)
                .toList();
    }
}

