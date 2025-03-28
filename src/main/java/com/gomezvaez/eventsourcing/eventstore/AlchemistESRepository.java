package com.gomezvaez.eventsourcing.eventstore;

import com.gomezvaez.eventsourcing.api.CreateAlchemistRequest;
import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;
import com.gomezvaez.eventsourcing.domain.event.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AlchemistESRepository {

    EventRepository eventRepository;

    public AlchemistESRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void registerExpense(ExpenseRegistered event) {
        registerEvent(event.solidify(new EventId(UUID.randomUUID().toString())));
    }

    public void registerActivity(ActivityRegistered event) {
        registerEvent(event.solidify(new EventId(UUID.randomUUID().toString())));
    }

    public void registerEvent(Event event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDate(new Date());
        eventEntity.setAlchemistId(event.alchemistId());
        eventEntity.setEvent(event);

        eventRepository.save(eventEntity);
    }

    public ArrayList<Alchemist> getAlchemistList() {
        List<EventEntity> events = eventRepository.findAllByOrderByIdAsc();
        Map<String, Alchemist> alchemists = new HashMap<>();
        events.forEach(eventEntity -> {
            String alchemistId = eventEntity.getAlchemistId().internalId();
            Event event = eventEntity.getEvent();
            if (!alchemists.containsKey(alchemistId)) {
                alchemists.put(alchemistId, new Alchemist());
            }
            event.applyTo(alchemists.get(alchemistId));
        });
        return new ArrayList<>(alchemists.values());
    }

    public Alchemist getAlchemist(AlchemistId id) {
        List<EventEntity> events = eventRepository.findByAlchemistIdOrderByIdAsc(id);
        Alchemist alchemist = new Alchemist();
        events.forEach(eventEntity -> eventEntity.getEvent().applyTo(alchemist));
        return alchemist;
    }

    public AlchemistId createAlchemist(CreateAlchemistRequest createAlchemistRequest) {
        AlchemistId alchemistId = new AlchemistId(UUID.randomUUID().toString());
        AlchemistCreated alchemistCreated = new AlchemistCreated(alchemistId, createAlchemistRequest.name(), createAlchemistRequest.email());
        registerEvent(alchemistCreated);
        return alchemistId;
    }
}
