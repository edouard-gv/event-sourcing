package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.PearlExpense;
import com.gomezvaez.eventsourcing.eventstore.Event;

import java.util.Date;

public record PearlsSpentEvent(String alchemistId, Date date, String description, int pearlsSpent) implements Event {

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getPearlsSpendings().add(new PearlExpense(description, date, pearlsSpent));
        alchemist.setPearls(alchemist.getPearls() - pearlsSpent);
    }
}