package com.gomezvaez.eventsourcing.domain;

import java.util.Date;

public class Activity {
    private String description;
    private Date date;
    private int perlsGained;

    // Constructeurs
    public Activity() {}

    public Activity(String description, Date date, int perlsGained) {
        this.description = description;
        this.date = date;
        this.perlsGained = perlsGained;
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

    public int getPerlsGained() {
        return perlsGained;
    }

    public void setPerlsGained(int perlsGained) {
        this.perlsGained = perlsGained;
    }
}