package com.gomezvaez.eventsourcing.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gomezvaez.eventsourcing.domain.Alchemist;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActivityRealizedEvent.class, name = "activityRealized"),
        @JsonSubTypes.Type(value = PearlsSpentEvent.class, name = "pearlsSpent"),
        @JsonSubTypes.Type(value = AlchemistCreatedEvent.class, name = "alchemistCreated")
})

public interface Event {
    String alchemistId();
    void applyTo(Alchemist alchemist);
}
