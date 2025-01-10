package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.exception.AttendeeNotFoundExcpetion;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.dao.impl.AttendeeJpa;
import edu.bbte.idde.kmim2248.model.Attendee;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.dto.AttendeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeJpa attendeeJpa;
    @Autowired
    private EventService eventService;


    public AttendeeDTO saveAttendee(Attendee attendee) {
        return AttendeeMapper.toAttendeeDTO(attendeeJpa.save(attendee));
    }

    public void deleteAttendeeFromEvent(Event event, Long attendeeId)
            throws AttendeeNotFoundExcpetion, DaoOperationException, EventNotFoundException {
        Attendee attendee = attendeeJpa.findById(attendeeId).orElseThrow(() ->
                new AttendeeNotFoundExcpetion("Attendee not found with id: " + attendeeId));
        if (Objects.equals(attendee.getEvent().getId(), event.getId())) {

            event.getAttendees().remove(attendee);
            eventService.updateEvent(event.getId(), EventMapper.toEventInDTO(event));
            attendeeJpa.delete(attendee);
        } else {
            throw new AttendeeNotFoundExcpetion("Attendee does not belong to the event.");
        }
    }
}
