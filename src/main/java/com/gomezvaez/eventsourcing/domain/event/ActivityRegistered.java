package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Activity;
import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;

import java.util.Date;

public record ActivityRegistered(EventId eventId, AlchemistId alchemistId, Date date, String description, int credit) implements CreationEvent {

    public ActivityRegistered(AlchemistId alchemistId, Date date, String description, int credit) {
        this(null, alchemistId, date, description, credit);
    }

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getActivities().add(new Activity(eventId, description, date, credit));
        alchemist.setBalance(alchemist.getBalance() + credit);
    }

    public ActivityRegistered setId(EventId eventId) {
        return new ActivityRegistered(eventId, alchemistId, date, description, credit);
    }
}