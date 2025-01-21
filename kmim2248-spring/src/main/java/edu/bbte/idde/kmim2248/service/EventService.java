package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.EventSpecification;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.service.dto.EventFilterDTO;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import java.util.Optional;



@Service
@EnableCaching
public class EventService {

    private final EventDao eventDao;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventDao eventDao, EventMapper eventMapper) {
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    @Cacheable(value = "events", key = "#id")
    public EventOutDTO getEventById(Long id) throws EventNotFoundException {
        Optional<Event> event = eventDao.findById(id);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found with id: " + id);
        } else {
            return eventMapper.toEventOutDTO(event.get());
        }
    }

    @Cacheable(value = "allEvents", key="#pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort.toString()")
    public Page<EventOutDTO> getAllEvents(Pageable pageable) {
        return eventDao.findAll(pageable).map(eventMapper::toEventOutDTO);
    }

    @Caching(evict = {
            @CacheEvict(value = "events", key = "#id"),
            @CacheEvict(value = "allEvents", allEntries = true)
    })
    public EventOutDTO updateEvent(Long id, EventInDTO eventDTO) throws EventNotFoundException {
        Optional<Event> existingEvent = eventDao.findById(id);
        eventDao.save(eventMapper.toEvent(eventDTO, id));
        if (existingEvent.isEmpty()) {
            throw new EventNotFoundException("Event not found with id: " + id);
        } else {
            return eventMapper.toEventOutDTO(existingEvent.get());
        }
    }

    @CacheEvict(value = "events", key = "#id")
    public void deleteEvent(Long id) throws EventNotFoundException {
        eventDao.deleteById(id);
    }


    @Caching(evict = {
            @CacheEvict(value = "events", allEntries = true),
            @CacheEvict(value = "allEvents", allEntries = true)
    })
    public EventOutDTO createEvent(EventInDTO eventDTO) {
        Event event = eventMapper.toEvent(eventDTO, null);
        eventDao.save(event);
        return eventMapper.toEventOutDTO(event);
    }

    @Cacheable(value = "searchResults", key = "#keyword + '_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort.toString()")
    public Page<EventOutDTO> searchEntities(String keyword, Pageable pageable) {
        return eventDao.findByNameContainingIgnoreCase(keyword, pageable).map(eventMapper::toEventOutDTO);
    }

    @Cacheable(value = "filteredEvents", key = "#filterDTO + '_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort.toString()")
    public Page<EventOutDTO> filterEvents(EventFilterDTO filterDTO, Pageable pageable) {
        return eventDao.findAll(EventSpecification.filterEvent(filterDTO), pageable)
                .map(eventMapper::toEventOutDTO);
    }
}


