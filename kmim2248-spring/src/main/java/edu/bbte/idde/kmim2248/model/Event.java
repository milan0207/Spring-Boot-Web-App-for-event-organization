package edu.bbte.idde.kmim2248.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")

public class Event extends BaseEntity {

    private String name;
    private String place;
    private LocalDate date;
    private Boolean online;
    private int duration;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //az eager az idoszakos taszk miatt, lazy fetch el nem talalta meg az adatokat
    private List<Attendee> attendees = new ArrayList<>();

    public Event(String name, String place, LocalDate date, Boolean online, int duration) {
        super();
        this.name = name;
        this.place = place;
        this.date = date;
        this.online = online;
        this.duration = duration;
    }
}


