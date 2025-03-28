package com.gomezvaez.eventsourcing.domain;

import java.util.Date;

public record Activity(String description, Date date, int pearlsGained) {
}