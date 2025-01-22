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
import edu.bbte.idde.kmim2248.service.dto.AttendeeFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public ResponseEntity<Page<AttendeeDTO>> getAttendeesByEvent(
            @PathVariable Long eventId,
            AttendeeFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
            throws DaoOperationException, EventNotFoundException {

        Event event = EventMapper.toEvent(eventService.getEventById(eventId));
        List<AttendeeDTO> attendeeDTOs = AttendeeMapper.toAttendeeDTOList(event.getAttendees());

        Pageable pageable = PageRequest.of(page, size,
                "asc".equalsIgnoreCase(direction)
                        ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        boolean hasFilters = filterDTO.getName() != null || filterDTO.getEmail() != null;
        if (hasFilters) {
            return ResponseEntity.ok(attendeeService.filterAttendees(filterDTO, pageable));
        }

        int start = Math.min((int) pageable.getOffset(), attendeeDTOs.size());
        int end = Math.min(start + pageable.getPageSize(), attendeeDTOs.size());
        List<AttendeeDTO> paginatedAttendees = attendeeDTOs.subList(start, end);

        Page<AttendeeDTO> attendeePage = new org.springframework.data.domain.PageImpl<>(
                paginatedAttendees, pageable, attendeeDTOs.size());

        return ResponseEntity.ok(attendeePage);
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
