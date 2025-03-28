package com.gomezvaez.eventsourcing.domain;

import java.util.Date;

public class Activity {
    private String description;
    private Date date;
    private int pearlsGained;

    // Constructeurs
    public Activity() {}

    public Activity(String description, Date date, int pearlsGained) {
        this.description = description;
        this.date = date;
        this.pearlsGained = pearlsGained;
    }

    // Getters et Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPearlsGained() {
        return pearlsGained;
    }

    public void setPearlsGained(int pearlsGained) {
        this.pearlsGained = pearlsGained;
    }
}