package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.service.dto.AttendeeDTO;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<EventOutDTO> getAllEvents(Pageable pageable) {
        return eventDao.findAll(pageable).map(eventMapper::toEventOutDTO);
    }

    public EventOutDTO getEventById(Long id) throws EventNotFoundException {

        Optional<Event> event = eventDao.findById(id);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found with id: " + id);
        } else {
            return eventMapper.toEventOutDTO(event.get());
        }
    }

    public EventOutDTO createEvent(EventInDTO eventDTO) {
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

    public void deleteEvent(Long id) {
        eventDao.deleteById(id);
    }

    public Page<EventOutDTO> searchEntities(String keyword, Pageable pageable) {
        return eventDao.findByNameContainingIgnoreCase(keyword, pageable).map(eventMapper::toEventOutDTO);
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


