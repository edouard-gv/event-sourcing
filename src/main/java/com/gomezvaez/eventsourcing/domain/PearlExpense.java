package com.gomezvaez.eventsourcing.domain;

import java.util.Date;

public record PearlExpense(String description, Date date, int amount) {
}