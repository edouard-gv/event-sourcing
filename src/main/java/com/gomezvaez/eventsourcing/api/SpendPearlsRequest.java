package com.gomezvaez.eventsourcing.api;

import java.util.Date;

public record SpendPearlsRequest(Date date, String description, int pearlsSpent) {
}