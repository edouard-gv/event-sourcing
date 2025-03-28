package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;

public record AlchemistCreatedEvent(String alchemistId, String name, String email) implements Event {

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.setName(name);
        alchemist.setEmail(email);
        alchemist.setId(alchemistId);
    }
}