package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Activity;
import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.eventstore.Event;

import java.util.Date;

public record ActivityRealizedEvent(String alchemistId, Date date, String description, int pearlsGained) implements Event {

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getActivities().add(new Activity(description, date, pearlsGained));
        alchemist.setPearls(alchemist.getPearls() + pearlsGained);
    }
}