package com.gomezvaez.eventsourcing.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActivityRegistered.class, name = "activityRegistered"),
        @JsonSubTypes.Type(value = ExpenseRegistered.class, name = "expenseRegistered"),
        @JsonSubTypes.Type(value = AlchemistCreated.class, name = "alchemistCreated")
})

public interface Event {
    AlchemistId alchemistId();
    void applyTo(Alchemist alchemist);
}
