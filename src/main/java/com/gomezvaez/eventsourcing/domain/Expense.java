package com.gomezvaez.eventsourcing.domain;

import com.gomezvaez.eventsourcing.domain.event.EventId;

import java.util.Date;

public record Expense(EventId expenseId, String description, Date date, int debit) {
}