package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.model.EventDTO;
import edu.bbte.idde.kmim2248.model.Events;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDTO toDTO(Events event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setPlace(event.getPlace());
        dto.setDate(event.getDate());
        dto.setOnline(event.isOnline());
        dto.setDuration(event.getDuration());
        return dto;
    }

    public Events toEvent(EventDTO dto) {
        Events events = new Events();
        events.setId(dto.getId());
        events.setName(dto.getName());
        events.setPlace(dto.getPlace());
        events.setDate(dto.getDate());
        events.setOnline(dto.isOnline());
        events.setDuration(dto.getDuration());

        return events;
    }
}