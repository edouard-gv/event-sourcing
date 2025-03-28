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
        alchemistRepository.registerExpense(expenseRegistered);
    }

    @PostMapping("/{alchemistId}/activities")
    public void registerActivity(@PathVariable String alchemistId, @RequestBody RegisterActivityRequest registerActivityRequest) {
        ActivityRegistered activityRegistered = new ActivityRegistered(new AlchemistId(alchemistId), registerActivityRequest.date(), registerActivityRequest.description(), registerActivityRequest.credit());
        alchemistRepository.registerActivity(activityRegistered);
    }

    @PostMapping
    public String createAlchemist(@RequestBody CreateAlchemistRequest createAlchemistRequest) {
        AlchemistId alchemistId = alchemistRepository.createAlchemist(createAlchemistRequest);
        // Returning a AlchemistId presuming that it would be converted to String, using forJson(), fails: double quotes are added to the returned value :-/.
        return alchemistId.forJson();
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