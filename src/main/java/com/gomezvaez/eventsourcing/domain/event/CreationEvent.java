package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.TechnicalId;

public interface CreationEvent extends Event {
    CreationEvent setId(TechnicalId technicalId);
}
