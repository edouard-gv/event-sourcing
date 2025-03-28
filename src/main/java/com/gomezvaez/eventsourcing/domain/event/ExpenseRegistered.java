package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.Expense;

import java.util.Date;

public record ExpenseRegistered(String alchemistId, Date date, String description, int debit) implements Event {

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.expenses().add(new Expense(description, date, debit));
        alchemist.setBalance(alchemist.getBalance() - debit);
    }
}