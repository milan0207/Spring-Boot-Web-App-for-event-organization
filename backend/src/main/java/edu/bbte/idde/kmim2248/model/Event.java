package edu.bbte.idde.kmim2248.model;

import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private String place;
    private LocalDate date;
    private Boolean online;
    private int duration;

    public Event(String name, String place, LocalDate date, Boolean online, int duration) {
        this.name = name;
        this.place = place;
        this.date = date;
        this.online = online;
        this.duration = duration;
    }

    public Event(int id, String name, String place, LocalDate date, Boolean online, int duration) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.date = date;
        this.online = online;
        this.duration = duration;
    }

    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
