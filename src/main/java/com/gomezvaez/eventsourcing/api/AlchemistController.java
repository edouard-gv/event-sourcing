package com.gomezvaez.eventsourcing.api;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.event.ActivityRealizedEvent;
import com.gomezvaez.eventsourcing.domain.event.AlchemistCreatedEvent;
import com.gomezvaez.eventsourcing.domain.event.PearlsSpentEvent;
import com.gomezvaez.eventsourcing.eventstore.Event;
import com.gomezvaez.eventsourcing.eventstore.EventEntity;
import com.gomezvaez.eventsourcing.eventstore.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/alchemists")
public class AlchemistController {

    private final EventRepository eventRepository;

    public AlchemistController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public String createAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest) {
        String alchemistId = UUID.randomUUID().toString();
        AlchemistCreatedEvent alchemistCreatedEvent = new AlchemistCreatedEvent(alchemistId, createAlchemistRequest.name(), createAlchemistRequest.email());
        registerEvent(alchemistCreatedEvent);
        return alchemistId;
    }

    @PostMapping("/{alchemistId}/spend-pearls")
    public void spendPearls(@PathVariable String alchemistId, @RequestBody SpendPearlsRequest spendPearlsRequest) {
        PearlsSpentEvent pearlsSpentEvent = new PearlsSpentEvent(alchemistId, spendPearlsRequest.date(), spendPearlsRequest.description(), spendPearlsRequest.pearlsSpent());
        registerEvent(pearlsSpentEvent);
    }

    @PostMapping("/{alchemistId}/realize-activity")
    public void realizeActivity(@PathVariable String alchemistId, @RequestBody RealizeActivityRequest realizeActivityRequest) {
        ActivityRealizedEvent activityRealizedEvent = new ActivityRealizedEvent(alchemistId, realizeActivityRequest.date(), realizeActivityRequest.description(), realizeActivityRequest.pearlsGained());
        registerEvent(activityRealizedEvent);
    }

    @GetMapping
    public List<Alchemist> alchemistList() {
        List<EventEntity> events = eventRepository.findAllByOrderByIdAsc();
        Map<String, Alchemist> alchemists = new HashMap<>();
        events.forEach(eventEntity -> {
            String alchemistId = eventEntity.getAlchemistId();
            Event event = eventEntity.getEvent();
            if (!alchemists.containsKey(alchemistId)) {
                alchemists.put(alchemistId, new Alchemist());
            }
            event.applyTo(alchemists.get(alchemistId));
        });
        return new ArrayList<>(alchemists.values());
    }

    @GetMapping("/{id}")
    public Alchemist alchemistDetails(@PathVariable String id) {
        List<EventEntity> events = eventRepository.findByAlchemistIdOrderByIdAsc(id);
        Alchemist alchemist = new Alchemist();
        events.forEach(eventEntity -> eventEntity.getEvent().applyTo(alchemist));
        return alchemist;
    }

    private void registerEvent(Event event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDate(new Date());
        eventEntity.setAlchemistId(event.alchemistId());
        eventEntity.setEvent(event);

        eventRepository.save(eventEntity);
    }
}