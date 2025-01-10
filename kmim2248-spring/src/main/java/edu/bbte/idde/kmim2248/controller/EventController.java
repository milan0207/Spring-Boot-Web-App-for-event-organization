package edu.bbte.idde.kmim2248.controller;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.service.dto.ResponseDTO;
import edu.bbte.idde.kmim2248.service.EventService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Get all entities
    @GetMapping
    public ResponseEntity<List<EventOutDTO>> getAllEntities(@RequestParam(required = false) String name)
            throws DaoOperationException {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(eventService.searchEntities(name));
        }
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventOutDTO> getEventById(@PathVariable Long id)
            throws DaoOperationException, EventNotFoundException {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // Create new event
    @PostMapping
    public ResponseEntity<EventOutDTO> createEvent(@Valid @RequestBody EventInDTO eventDTO, BindingResult result)
            throws InvalidEventException, DaoOperationException, EventNotFoundException {
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
    public ResponseEntity<ResponseDTO> deleteEvent(@PathVariable Long id)
            throws DaoOperationException, EventNotFoundException {
        eventService.deleteEvent(id);
        ResponseDTO responseDTO = new ResponseDTO("Event deleted successfully", 200);
        return ResponseEntity.ok(responseDTO);
    }
}
