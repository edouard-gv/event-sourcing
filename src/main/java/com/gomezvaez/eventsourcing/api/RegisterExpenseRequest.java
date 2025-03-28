package com.gomezvaez.eventsourcing.api;

import java.util.Date;

public record RegisterExpenseRequest(Date date, String description, int debit) {
}