package com.gomezvaez.eventsourcing.api;

import com.gomezvaez.eventsourcing.domain.Alchemist;
import com.gomezvaez.eventsourcing.domain.AlchemistId;
import com.gomezvaez.eventsourcing.domain.event.ActivityRegistered;
import com.gomezvaez.eventsourcing.domain.event.ExpenseRegistered;
import com.gomezvaez.eventsourcing.eventstore.AlchemistESRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alchemists")
public class AlchemistController {

    private final AlchemistESRepository alchemistRepository;

    public AlchemistController(AlchemistESRepository alchemistRepository) {
        this.alchemistRepository = alchemistRepository;
    }

    @PostMapping("/{alchemistId}/expenses")
    public void registerExpense(@PathVariable String alchemistId, @RequestBody RegisterExpenseRequest registerExpenseRequest) {
        ExpenseRegistered expenseRegistered = new ExpenseRegistered(new AlchemistId(alchemistId), registerExpenseRequest.date(), registerExpenseRequest.description(), registerExpenseRequest.debit());
        alchemistRepository.registerEvent(expenseRegistered);
    }

    @PostMapping("/{alchemistId}/activities")
    public void registerActivity(@PathVariable String alchemistId, @RequestBody RegisterActivityRequest registerActivityRequest) {
        ActivityRegistered activityRegistered = new ActivityRegistered(new AlchemistId(alchemistId), registerActivityRequest.date(), registerActivityRequest.description(), registerActivityRequest.credit());
        alchemistRepository.registerEvent(activityRegistered);
    }

    @PostMapping
    public String createAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest) {
        return alchemistRepository.createAlchemist(createAlchemistRequest).internalId();
    }

    @GetMapping
    public List<Alchemist> alchemistList() {
        return alchemistRepository.getAlchemistList();
    }

    @GetMapping("/{alchemistId}")
    public Alchemist alchemistDetails(@PathVariable String alchemistId) {
        return alchemistRepository.getAlchemist(new AlchemistId(alchemistId));
    }

}