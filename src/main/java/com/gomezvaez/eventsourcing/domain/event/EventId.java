package com.gomezvaez.eventsourcing.domain.event;

import com.fasterxml.jackson.annotation.JsonValue;

public record EventId(String internalId) {

    @JsonValue
    public String forJson() {
        return internalId;
    }
}
