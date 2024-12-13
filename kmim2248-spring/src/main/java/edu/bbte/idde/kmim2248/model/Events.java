package edu.bbte.idde.kmim2248.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Events {

    private Long id;
    private String name;
    private String place;
    private LocalDate date;
    private Boolean online;
    private int duration;

    public Events(String name, String place, LocalDate date, Boolean online, int duration) {
        this.name = name;
        this.place = place;
        this.date = date;
        this.online = online;
        this.duration = duration;
    }

    public Boolean isOnline() {
        return online;
    }


}


