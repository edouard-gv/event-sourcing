package com.gomezvaez.eventsourcing.api;

import com.gomezvaez.eventsourcing.domain.Alchemist;
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
        ExpenseRegistered expenseRegistered = new ExpenseRegistered(alchemistId, registerExpenseRequest.date(), registerExpenseRequest.description(), registerExpenseRequest.debit());
        alchemistRepository.registerEvent(expenseRegistered);
    }

    @PostMapping("/{alchemistId}/activities")
    public void registerActivity(@PathVariable String alchemistId, @RequestBody RegisterActivityRequest registerActivityRequest) {
        ActivityRegistered activityRegistered = new ActivityRegistered(alchemistId, registerActivityRequest.date(), registerActivityRequest.description(), registerActivityRequest.credit());
        alchemistRepository.registerEvent(activityRegistered);
    }

    @PostMapping
    public String newAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest) {
        return alchemistRepository.createAlchemist(createAlchemistRequest);
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