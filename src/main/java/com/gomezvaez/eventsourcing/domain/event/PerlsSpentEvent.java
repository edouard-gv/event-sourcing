package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.PerlExpense;
import com.gomezvaez.eventsourcing.eventstore.Event;

import java.util.Date;

public class PerlsSpentEvent implements Event {
    private String alchemistId;
    private String description;
    private Date date;
    private int perlsSpent;

    public String getAlchemistId() {
        return alchemistId;
    }

    public void setAlchemistId(String alchemistId) {
        this.alchemistId = alchemistId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPerlsSpent() {
        return perlsSpent;
    }

    public void setPerlsSpent(int perlsSpent) {
        this.perlsSpent = perlsSpent;
    }

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getPerlsSpendings().add(new PerlExpense(description, date, perlsSpent));
        alchemist.setPerls(alchemist.getPerls() - perlsSpent);
    }
}