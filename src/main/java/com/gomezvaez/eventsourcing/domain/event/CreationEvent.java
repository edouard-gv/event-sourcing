package com.gomezvaez.eventsourcing.domain.event;

public interface CreationEvent extends Event {
    CreationEvent setId(String internalId);
}
