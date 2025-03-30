package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;
import com.gomezvaez.eventsourcing.domain.Expense;

import java.util.Date;

public record ExpenseRegistered(EventId eventId, AlchemistId alchemistId, Date date, String description, int debit) implements CreationEvent {

    public ExpenseRegistered(AlchemistId alchemistId, Date date, String description, int debit) {
        this(null, alchemistId, date, description, debit);
    }

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getExpenses().add(new Expense(eventId, description, date, debit));
        alchemist.setBalance(alchemist.getBalance() - debit);
    }

    public ExpenseRegistered setId(String internalId) {
        return new ExpenseRegistered(new EventId(internalId), alchemistId, date, description, debit);
    }
}