package com.gomezvaez.eventsourcing.api;

import java.util.Date;

public record RealizeActivityRequest(Date date, String description, int perlsGained) {
}