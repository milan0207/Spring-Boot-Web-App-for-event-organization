package edu.bbte.idde.kmim2248.controller;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.service.dto.EventFilterDTO;
import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.service.dto.ResponseDTO;
import edu.bbte.idde.kmim2248.service.EventService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<EventOutDTO>> filterEvents(EventFilterDTO filterDTO, Pageable pageable) {
        return ResponseEntity.ok(eventService.filterEvents(filterDTO, pageable));
    }
    @GetMapping
    public ResponseEntity<Page<EventOutDTO>> getAllEntities(
            @RequestParam(required = false) String name,
            @RequestParam(required = false)  String place,
            @RequestParam(required = false)  LocalDate minDate,
            @RequestParam(required = false)  LocalDate maxDate,
            @RequestParam(defaultValue = "-1",required = false) int minDuration,
            @RequestParam(defaultValue = "-1",required = false) int maxDuration,
            @RequestParam(required = false)  Boolean online,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size,
                "asc".equalsIgnoreCase(direction)
                        ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        boolean hasFilters = name != null || place != null || minDate != null || maxDate != null
                || minDuration != -1 || maxDuration != -1 || online != null;

        if (hasFilters) {
            EventFilterDTO filterDTO = new EventFilterDTO();
            filterDTO.setName(name);
            filterDTO.setPlace(place);
            filterDTO.setMinDate(minDate);
            filterDTO.setMaxDate(maxDate);
            if (minDuration != -1) {
                filterDTO.setMinDuration(minDuration);
            }
            if (maxDuration != -1) {
                filterDTO.setMaxDuration(maxDuration);
            }

            filterDTO.setOnline(online);

            return ResponseEntity.ok(eventService.filterEvents(filterDTO, pageable));
        }



        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(eventService.searchEntities(name, pageable));
        }
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventOutDTO> getEventById(@PathVariable Long id)
            throws EventNotFoundException {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // Create new event
    @PostMapping
    public ResponseEntity<EventOutDTO> createEvent(@Valid @RequestBody EventInDTO eventDTO, BindingResult result)
            throws InvalidEventException {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            throw new InvalidEventException("Validation failed, " + errorMessages);
        }
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    // Update event
    @PutMapping("/{id}")
    public ResponseEntity<EventOutDTO> updateEvent(@PathVariable Long id,
                                                   @Valid @RequestBody EventInDTO eventDTO, BindingResult result)
            throws InvalidEventException, DaoOperationException, EventNotFoundException {

        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            throw new InvalidEventException("Validation failed, " + errorMessages);
        }
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    // Delete event
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteEvent(@PathVariable Long id) throws DaoOperationException, EventNotFoundException {
        eventService.deleteEvent(id);
        ResponseDTO responseDTO = new ResponseDTO("Event deleted successfully", 200);
        return ResponseEntity.ok(responseDTO);
    }


}
