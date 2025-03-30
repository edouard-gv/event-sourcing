package com.gomezvaez.eventsourcing.domain.event;

import com.fasterxml.jackson.annotation.JsonValue;
import com.gomezvaez.eventsourcing.domain.TechnicalId;

public record EventId(String internalId) {

    @JsonValue
    public String forJson() {
        return internalId;
    }

    public static EventId fromTechnicalId(TechnicalId technicalId) {
        return new EventId(technicalId.internalId());
    }
}
