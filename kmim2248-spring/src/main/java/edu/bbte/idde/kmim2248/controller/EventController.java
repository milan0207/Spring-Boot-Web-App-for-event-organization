package edu.bbte.idde.kmim2248.controller;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.EventDTO;
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
    public ResponseEntity<List<EventDTO>> getAllEntities(@RequestParam(required = false) String name)
            throws DaoOperationException {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(eventService.searchEntities(name));
        }
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id)
            throws DaoOperationException, EventNotFoundException {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // Create new event
    @PostMapping
    public ResponseEntity<Object> createEvent(@Valid @RequestBody EventDTO eventDTO, BindingResult result)
            throws InvalidEventExcpetion, DaoOperationException {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            throw new InvalidEventExcpetion("Validation failed, " + errorMessages);
        }
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    // Update event
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable Long id,
                                              @Valid @RequestBody EventDTO eventDTO, BindingResult result)
            throws InvalidEventExcpetion, DaoOperationException, EventNotFoundException {

        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            throw new InvalidEventExcpetion("Validation failed, " + errorMessages);
        }
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    // Delete event
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id)
            throws DaoOperationException, EventNotFoundException {
        eventService.deleteEvent(id);
        Response response = new Response("Event deleted successfully", 200);
        return ResponseEntity.ok(response);
    }
}
