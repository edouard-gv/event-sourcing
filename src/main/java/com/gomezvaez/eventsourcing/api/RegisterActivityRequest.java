package com.gomezvaez.eventsourcing.api;

import java.util.Date;

public record RegisterActivityRequest(Date date, String description, int credit) {
}