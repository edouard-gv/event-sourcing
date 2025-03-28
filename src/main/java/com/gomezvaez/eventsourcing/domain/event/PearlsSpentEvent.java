package com.gomezvaez.eventsourcing.domain.event;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.PearlExpense;
import com.gomezvaez.eventsourcing.eventstore.Event;

import java.util.Date;

public class PearlsSpentEvent implements Event {
    private String alchemistId;
    private String description;
    private Date date;
    private int pearlsSpent;

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

    public int getPearlsSpent() {
        return pearlsSpent;
    }

    public void setPearlsSpent(int pearlsSpent) {
        this.pearlsSpent = pearlsSpent;
    }

    @Override
    public void applyTo(Alchemist alchemist) {
        alchemist.getPearlsSpendings().add(new PearlExpense(description, date, pearlsSpent));
        alchemist.setPearls(alchemist.getPearls() - pearlsSpent);
    }
}