package edu.bbte.idde.kmim2248.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventFilterDTO {
    private String name;
    private String place;
    private Boolean online;
    private LocalDate minDate;
    private LocalDate maxDate;
    private Integer minDuration;
    private Integer maxDuration;
}