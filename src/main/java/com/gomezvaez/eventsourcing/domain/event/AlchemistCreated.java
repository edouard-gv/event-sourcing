package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;

public record AlchemistCreated(AlchemistId alchemistId, String name, String email) implements CreationEvent {

    public AlchemistCreated(String name, String email) {
        this(null, name, email);
    }

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.setName(name);
        alchemist.setEmail(email);
        alchemist.setId(alchemistId);
    }

    @Override
    public CreationEvent setId(String internalId) {
        return new AlchemistCreated(new AlchemistId(internalId), name, email);
    }
}