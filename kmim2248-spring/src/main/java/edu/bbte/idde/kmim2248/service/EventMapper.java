package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.service.dto.EventInDTO;
import edu.bbte.idde.kmim2248.service.dto.EventOutDTO;
import edu.bbte.idde.kmim2248.model.Event;
import org.springframework.stereotype.Component;


@Component
public class EventMapper {

    public static Event toEvent(EventInDTO dto, Long id) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setPlace(dto.getPlace());
        event.setDate(dto.getDate());
        event.setOnline(dto.getOnline());
        event.setDuration(dto.getDuration());
        event.setId(id);
        return event;
    }

    public static EventInDTO toEventInDTO(Event event) {
        EventInDTO dto = new EventInDTO();
        dto.setName(event.getName());
        dto.setPlace(event.getPlace());
        dto.setDate(event.getDate());
        dto.setOnline(event.getOnline());
        dto.setDuration(event.getDuration());
        return dto;
    }

    public EventOutDTO toEventOutDTO(Event event) {
        EventOutDTO dto = new EventOutDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setPlace(event.getPlace());
        dto.setDate(event.getDate());
        dto.setOnline(event.getOnline());
        dto.setDuration(event.getDuration());
        dto.setAttendeesDTO(AttendeeMapper.toAttendeeDTOList(event.getAttendees()));
        return dto;
    }

    public static Event toEvent(EventOutDTO dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setPlace(dto.getPlace());
        event.setDate(dto.getDate());
        event.setOnline(dto.getOnline());
        event.setDuration(dto.getDuration());
        event.setAttendees(AttendeeMapper.toAttendeeList(dto.getAttendeesDTO()));
        return event;
    }
}
