package edu.bbte.idde.kmim2248.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Place is mandatory")
    private String place;

    @NotNull(message = "Date is mandatory")
    private LocalDate date;

    private Boolean online;

    @NotNull(message = "Duration is mandatory")
    private int duration;

    public Boolean isOnline() {
        return online;
    }
}
