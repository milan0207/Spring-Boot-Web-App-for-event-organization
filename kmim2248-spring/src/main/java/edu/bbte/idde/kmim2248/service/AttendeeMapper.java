package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.model.Attendee;
import edu.bbte.idde.kmim2248.service.dto.AttendeeDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AttendeeMapper {
    public static Attendee toAttendee(AttendeeDTO dto) {
        Attendee attendee = new Attendee();
        attendee.setName(dto.getName());
        attendee.setEmail(dto.getEmail());
        attendee.setPhone(dto.getPhone());
        attendee.setId(dto.getId());
        return attendee;
    }

    public static AttendeeDTO toAttendeeDTO(Attendee attendee) {
        AttendeeDTO dto = new AttendeeDTO();
        dto.setName(attendee.getName());
        dto.setEmail(attendee.getEmail());
        dto.setPhone(attendee.getPhone());
        dto.setId(attendee.getId());
        return dto;
    }

    public static List<AttendeeDTO> toAttendeeDTOList(List<Attendee> attendees) {
        return attendees.stream().map(AttendeeMapper::toAttendeeDTO).collect(Collectors.toList());
    }

    public static List<Attendee> toAttendeeList(List<AttendeeDTO> attendeesDTO) {
        return attendeesDTO.stream().map(AttendeeMapper::toAttendee).collect(Collectors.toList());
    }
}
