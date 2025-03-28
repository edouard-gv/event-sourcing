package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;

public record AlchemistCreated(AlchemistId alchemistId, String name, String email) implements Event {

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.setName(name);
        alchemist.setEmail(email);
        alchemist.setId(alchemistId);
    }
}