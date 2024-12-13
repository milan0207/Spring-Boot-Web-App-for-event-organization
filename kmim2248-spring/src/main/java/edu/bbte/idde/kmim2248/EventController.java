package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.EventDTO;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAllEntities(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            try {
                return ResponseEntity.ok(eventService.searchEntities(name));
            } catch (DaoOperationException e) {
                Response response = new Response("Error occurred while searching events", 500);
                return ResponseEntity.status(500).body(response);
            }
        }
        try {
            return ResponseEntity.ok(eventService.getAllEvents());
        } catch (DaoOperationException e) {
            Response response = new Response("Error occurred while fetching events", 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventService.getEventById(id));
        } catch (EventNotFoundException e) {
            Response response = new Response("Event not found with id " + id, 404);
            return ResponseEntity.status(404).body(response);
        } catch (DaoOperationException e) {
            Response response = new Response("Error occurred while fetching event", 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Create new event
    @PostMapping
    public ResponseEntity<Object> createEvent(@Valid @RequestBody EventDTO eventDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            Response response = new Response("Validation failed, " + errorMessages, 400);
            return ResponseEntity.status(400).body(response);
        }
        try {
            return ResponseEntity.ok(eventService.createEvent(eventDTO));
        } catch (DaoOperationException e) {
            Response response = new Response("Error occurred while creating event", 500);
            return ResponseEntity.status(500).body(response);
        }
    }


    // Update event
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable Long id,
                                              @Valid @RequestBody EventDTO eventDTO, BindingResult result) {

        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            Response response = new Response("Validation failed, " + errorMessages, 400);
            return ResponseEntity.status(400).body(response);
        }
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
        } catch (DaoOperationException e) {
            Response response = new Response("Error occurred while updating event", 500);
            return ResponseEntity.status(500).body(response);
        } catch (EventNotFoundException e) {
            Response response = new Response("Event not found with id " + id, 404);
            return ResponseEntity.status(404).body(response);
        }
    }

    // Delete event
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
        } catch (DaoOperationException e) {
            Response response = new Response("Error occurred while deleting event", 500);
            return ResponseEntity.status(500).body(response);
        } catch (EventNotFoundException e) {
            Response response = new Response("Event not found with id " + id, 404);
            return ResponseEntity.status(404).body(response);
        }
        Response response = new Response("Event deleted successfully", 200);
        return ResponseEntity.ok(response);
    }

}
