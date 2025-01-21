package edu.bbte.idde.kmim2248.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventInDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Place is mandatory")
    @Size(min = 3, max = 50, message = "Place must be between 3 and 50 characters")
    private String place;

    @NotNull(message = "Date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Boolean online;

    @NotNull(message = "Duration is mandatory")
    @Positive(message = "Duration must be positive")
    private int duration;

}
