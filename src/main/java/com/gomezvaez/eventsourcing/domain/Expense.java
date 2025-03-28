package com.gomezvaez.eventsourcing.domain;

import java.util.Date;

public record Expense(String description, Date date, int debit) {
}