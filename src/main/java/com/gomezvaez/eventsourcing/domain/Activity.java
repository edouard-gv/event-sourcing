package com.gomezvaez.eventsourcing.domain;

import com.gomezvaez.eventsourcing.domain.event.EventId;

import java.util.Date;

public record Activity(EventId activityId, String description, Date date, int credit) {
}