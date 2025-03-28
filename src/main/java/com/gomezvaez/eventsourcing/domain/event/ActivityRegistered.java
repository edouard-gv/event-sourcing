package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Activity;
import com.gomezvaez.eventsourcing.domain.Alchemist;

import java.util.Date;

public record ActivityRegistered(String alchemistId, Date date, String description, int credit) implements Event {

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.activities().add(new Activity(description, date, credit));
        alchemist.setBalance(alchemist.getBalance() + credit);
    }
}