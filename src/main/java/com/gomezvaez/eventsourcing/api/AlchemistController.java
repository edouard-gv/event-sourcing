package com.gomezvaez.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public String createAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest)  {
        AlchemistCreatedEvent alchemistCreatedEvent = new AlchemistCreatedEvent(UUID.randomUUID().toString(), createAlchemistRequest.name(), createAlchemistRequest.email());

        EventEntity event = new EventEntity();
        event.setDate(new Date());
        event.setAlchemistId(alchemistCreatedEvent.alchemistId());
        event.setEvent(alchemistCreatedEvent);

        eventRepository.save(event);

        return alchemistCreatedEvent.alchemistId();
    }

    @PostMapping("/{alchemistId}/spend-pearls")
    public void spendPearls(@PathVariable String alchemistId, @RequestBody SpendPearlsRequest spendPearlsRequest) {
        PearlsSpentEvent pearlsSpentEvent = new PearlsSpentEvent(alchemistId, spendPearlsRequest.date(), spendPearlsRequest.description(), spendPearlsRequest.pearlsSpent());

        EventEntity event = new EventEntity();
        event.setDate(new Date());
        event.setAlchemistId(alchemistId);
        event.setEvent(pearlsSpentEvent);

        eventRepository.save(event);
    }

    @PostMapping("/{alchemistId}/realize-activity")
    public void realizeActivity(@PathVariable String alchemistId, @RequestBody RealizeActivityRequest realizeActivityRequest) {
        ActivityRealizedEvent activityRealizedEvent = new ActivityRealizedEvent(alchemistId, realizeActivityRequest.date(), realizeActivityRequest.description(), realizeActivityRequest.pearlsGained());

        EventEntity event = new EventEntity();
        event.setDate(new Date());
        event.setAlchemistId(alchemistId);
        event.setEvent(activityRealizedEvent);

        eventRepository.save(event);
    }

    @GetMapping
    public List<Alchemist> listAlchemists() {
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
    public Alchemist detailAlchemist(@PathVariable String id) {
        List<EventEntity> events = eventRepository.findByAlchemistIdOrderByIdAsc(id);
        Alchemist alchemist = new Alchemist();
        events.stream().map(EventEntity::getEvent).forEach(event -> event.applyTo(alchemist));
        return alchemist;
    }
}