package com.gomezvaez.eventsourcing.eventstore;

import com.gomezvaez.eventsourcing.domain.event.Event;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String alchemistId;

    @Convert(converter = EventConverter.class)
    private Event event;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAlchemistId() {
        return alchemistId;
    }

    public void setAlchemistId(String alchemistId) {
        this.alchemistId = alchemistId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}