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
    public String createAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest) throws JsonProcessingException {
        AlchemistCreatedEvent alchemistCreatedEvent = new AlchemistCreatedEvent();
        alchemistCreatedEvent.setAlchemistId(UUID.randomUUID().toString());
        alchemistCreatedEvent.setName(createAlchemistRequest.name());
        alchemistCreatedEvent.setEmail(createAlchemistRequest.email());

        EventEntity event = new EventEntity();
        event.setDate(new Date());
        event.setAlchemistId(alchemistCreatedEvent.getAlchemistId());
        event.setEvent(alchemistCreatedEvent);

        eventRepository.save(event);

        return alchemistCreatedEvent.getAlchemistId();
    }

    @PostMapping("/{alchemistId}/spend-pearls")
    public void spendPearls(@PathVariable String alchemistId, @RequestBody SpendPearlsRequest spendPearlsRequest) throws JsonProcessingException {
        PearlsSpentEvent pearlsSpentEvent = new PearlsSpentEvent();
        pearlsSpentEvent.setAlchemistId(alchemistId);
        pearlsSpentEvent.setDate(spendPearlsRequest.date());
        pearlsSpentEvent.setDescription(spendPearlsRequest.description());
        pearlsSpentEvent.setPearlsSpent(spendPearlsRequest.pearlsSpent());

        EventEntity event = new EventEntity();
        event.setDate(new Date());
        event.setAlchemistId(alchemistId);
        event.setEvent(pearlsSpentEvent);

        eventRepository.save(event);
    }

    @PostMapping("/{alchemistId}/realize-activity")
    public void realizeActivity(@PathVariable String alchemistId, @RequestBody RealizeActivityRequest realizeActivityRequest) throws JsonProcessingException {
        ActivityRealizedEvent activityRealizedEvent = new ActivityRealizedEvent();
        activityRealizedEvent.setAlchemistId(alchemistId);
        activityRealizedEvent.setDate(realizeActivityRequest.date());
        activityRealizedEvent.setDescription(realizeActivityRequest.description());
        activityRealizedEvent.setPearlsGained(realizeActivityRequest.pearlsGained());

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
        events.stream().forEach(eventEntity -> {
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
        events.stream().map(ee -> ee.getEvent()).forEach(event -> event.applyTo(alchemist));
        return alchemist;
    }
}