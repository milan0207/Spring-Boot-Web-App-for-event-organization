package edu.bbte.idde.kmim2248.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeDTO {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Place is mandatory")
    @Size(min = 3, max = 50, message = "email must be between 3 and 50 characters")
    private String email;
    @NotBlank(message = "Place is mandatory")
    @Size(min = 3, max = 10, message = "phone must be between 3 and 10 characters")
    private String phone;


}
