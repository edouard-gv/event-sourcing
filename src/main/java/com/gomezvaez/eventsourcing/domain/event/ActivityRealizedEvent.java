package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Activity;
import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.eventstore.Event;

import java.util.Date;

public class ActivityRealizedEvent implements Event {
    private String alchemistId;
    private String description;
    private Date date;
    private int perlsGained;

    public String getAlchemistId() {
        return alchemistId;
    }

    public void setAlchemistId(String alchemistId) {
        this.alchemistId = alchemistId;
    }

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

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getActivities().add(new Activity(description, date, perlsGained));
        alchemist.setPerls(alchemist.getPerls() + perlsGained);
    }
}