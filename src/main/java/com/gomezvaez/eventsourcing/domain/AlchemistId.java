package com.gomezvaez.eventsourcing.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record AlchemistId(String internalId) {

    @JsonValue
    public String forJson() {
        return internalId;
    }
}
