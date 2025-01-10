package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.service.dto.AttendeeDTO;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {


    @Autowired
    private final EventDao eventDao;
    @Autowired
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventDao eventDao, EventMapper eventMapper) {
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    public List<EventOutDTO> getAllEvents() throws DaoOperationException {
        return eventDao.findAll().stream()
                .map(eventMapper::toEventOutDTO)
                .toList();
    }

    public EventOutDTO getEventById(Long id) throws EventNotFoundException, DaoOperationException {

        Optional<Event> event = eventDao.findById(id);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found with id: " + id);
        } else {
            return eventMapper.toEventOutDTO(event.get());
        }
    }

    public EventOutDTO createEvent(EventInDTO eventDTO) throws DaoOperationException, EventNotFoundException {
        Event event = eventMapper.toEvent(eventDTO, null);
        eventDao.save(event);
        return eventMapper.toEventOutDTO(event);
    }

    public EventOutDTO updateEvent(Long id, EventInDTO eventDTO)
            throws DaoOperationException, EventNotFoundException {
        Optional<Event> existingEvent = eventDao.findById(id);

        eventDao.save(eventMapper.toEvent(eventDTO, id));
        if (existingEvent.isEmpty()) {
            throw new EventNotFoundException("Event not found with id: " + id);
        } else {
            return eventMapper.toEventOutDTO(existingEvent.get());
        }
    }

    public void deleteEvent(Long id) throws DaoOperationException, EventNotFoundException {
        eventDao.deleteById(id);
    }

    public List<EventOutDTO> searchEntities(String keyword) throws DaoOperationException {
        return eventDao.findByNameContainingIgnoreCase(keyword).stream()
                .map(eventMapper::toEventOutDTO)
                .toList();
    }

    public List<AttendeeDTO> getAttendeesByEvent(Long eventId)
            throws DaoOperationException, EventNotFoundException {
        Event event = eventDao.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event not found with id: " + eventId));
        return event.getAttendees().stream()
                .map(AttendeeMapper::toAttendeeDTO)
                .toList();
    }

}


