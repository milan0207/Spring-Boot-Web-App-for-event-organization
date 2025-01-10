package edu.bbte.idde.kmim2248.controller;

import edu.bbte.idde.kmim2248.dao.exception.AttendeeNotFoundExcpetion;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Attendee;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.AttendeeMapper;
import edu.bbte.idde.kmim2248.service.AttendeeService;
import edu.bbte.idde.kmim2248.service.EventMapper;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.service.dto.AttendeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Profile("jpa")
public class EventAttendeeController {

    @Autowired
    private EventService eventService;
    @Autowired
    private AttendeeService attendeeService;

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<List<AttendeeDTO>> getAttendeesByEvent(@PathVariable Long eventId)
            throws DaoOperationException, EventNotFoundException {
        Event event = EventMapper.toEvent(eventService.getEventById(eventId));
        return ResponseEntity.ok(AttendeeMapper.toAttendeeDTOList(event.getAttendees()));
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeDTO> addAttendeeToEvent(@PathVariable Long eventId, @RequestBody Attendee attendee)
            throws DaoOperationException, EventNotFoundException {
        Event event = EventMapper.toEvent(eventService.getEventById(eventId));

        event.getAttendees().add(attendee);
        eventService.updateEvent(event.getId(), EventMapper.toEventInDTO(event));
        attendee.setEvent(event);
        AttendeeDTO savedAttendee = attendeeService.saveAttendee(attendee);
        return ResponseEntity.ok(savedAttendee);
    }


    @DeleteMapping("/{eventId}/attendees/{attendeeId}")
    public ResponseEntity<Void> deleteAttendeeFromEvent(@PathVariable Long eventId, @PathVariable Long attendeeId)
            throws DaoOperationException, EventNotFoundException, AttendeeNotFoundExcpetion {
        Event event = EventMapper.toEvent(eventService.getEventById(eventId));
        attendeeService.deleteAttendeeFromEvent(event, attendeeId);
        return ResponseEntity.ok().build();
    }
}
