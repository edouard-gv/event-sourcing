package com.gomezvaez.eventsourcing.eventstore;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;
import com.gomezvaez.eventsourcing.domain.AlchemistNotFoundException;
import com.gomezvaez.eventsourcing.domain.TechnicalId;
import com.gomezvaez.eventsourcing.domain.event.CreationEvent;
import com.gomezvaez.eventsourcing.domain.event.Event;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AlchemistESRepository {

    EventRepository eventRepository;

    public AlchemistESRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event registerEvent(Event event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDate(new Date());
        Event eventWithId = setId(event);
        eventEntity.setAlchemistId(eventWithId.alchemistId());
        eventEntity.setEvent(eventWithId);

        eventRepository.save(eventEntity);

        return eventWithId;

    }

    private static Event setId(Event event) {
        return event instanceof CreationEvent creationEvent ? creationEvent.setId(new TechnicalId(UUID.randomUUID().toString())) : event;
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
        if (alchemist.getId() == null) {
            throw new AlchemistNotFoundException(id.forJson());
        }
        return alchemist;
    }

}
