package edu.bbte.idde.kmim2248.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendees")
public class Attendee extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    @ManyToOne(optional = false)
    private Event event;
}