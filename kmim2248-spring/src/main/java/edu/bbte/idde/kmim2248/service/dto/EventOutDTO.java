package edu.bbte.idde.kmim2248.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOutDTO implements Serializable {

    private Long id;

    private String name;

    private String place;

    private LocalDate date;

    private Boolean online;

    private int duration;

    private List<AttendeeDTO> attendeesDTO = new ArrayList<>();
}
