package com.gomezvaez.eventsourcing.api;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.event.ActivityRealizedEvent;
import com.gomezvaez.eventsourcing.domain.event.AlchemistCreatedEvent;
import com.gomezvaez.eventsourcing.domain.event.PearlsSpentEvent;
import com.gomezvaez.eventsourcing.eventstore.AlchemistESRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alchemists")
public class AlchemistController {

    private final AlchemistESRepository alchemistRepository;

    public AlchemistController(AlchemistESRepository alchemistRepository) {
        this.alchemistRepository = alchemistRepository;
    }

    @PostMapping
    public String createAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest) {
        String alchemistId = UUID.randomUUID().toString();
        AlchemistCreatedEvent alchemistCreatedEvent = new AlchemistCreatedEvent(alchemistId, createAlchemistRequest.name(), createAlchemistRequest.email());
        alchemistRepository.registerEvent(alchemistCreatedEvent);
        return alchemistId;
    }

    @PostMapping("/{alchemistId}/spend-pearls")
    public void spendPearls(@PathVariable String alchemistId, @RequestBody SpendPearlsRequest spendPearlsRequest) {
        PearlsSpentEvent pearlsSpentEvent = new PearlsSpentEvent(alchemistId, spendPearlsRequest.date(), spendPearlsRequest.description(), spendPearlsRequest.pearlsSpent());
        alchemistRepository.registerEvent(pearlsSpentEvent);
    }

    @PostMapping("/{alchemistId}/realize-activity")
    public void realizeActivity(@PathVariable String alchemistId, @RequestBody RealizeActivityRequest realizeActivityRequest) {
        ActivityRealizedEvent activityRealizedEvent = new ActivityRealizedEvent(alchemistId, realizeActivityRequest.date(), realizeActivityRequest.description(), realizeActivityRequest.pearlsGained());
        alchemistRepository.registerEvent(activityRealizedEvent);
    }

    @GetMapping
    public List<Alchemist> alchemistList() {
        return alchemistRepository.getAlchemistList();
    }

    @GetMapping("/{id}")
    public Alchemist alchemistDetails(@PathVariable String id) {
        return alchemistRepository.getAlchemist(id);
    }

}