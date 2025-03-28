package com.gomezvaez.eventsourcing.domain;

import java.util.Date;

public record PerlExpense(String description, Date date, int amount) {
}