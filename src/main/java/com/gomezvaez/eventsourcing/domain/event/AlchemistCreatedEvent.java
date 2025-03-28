package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.eventstore.Event;

public class AlchemistCreatedEvent implements Event {
    private String alchemistId;
    private String name;
    private String email;

    // Constructeurs
    public AlchemistCreatedEvent() {}

    public AlchemistCreatedEvent(String alchemistId, String name, String email) {
        this.alchemistId = alchemistId;
        this.name = name;
        this.email = email;
    }

    // Getters et Setters
    public String getAlchemistId() {
        return alchemistId;
    }

    public void setAlchemistId(String alchemistId) {
        this.alchemistId = alchemistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.setName(name);
        alchemist.setEmail(email);
        alchemist.setId(alchemistId);
    }
}