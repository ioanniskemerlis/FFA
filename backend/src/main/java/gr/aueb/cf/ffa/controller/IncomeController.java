package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public Income addIncome(@RequestBody Income income, Authentication authentication) {
        // Extract the username (not the full User object)
        String username = authentication.getName();

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Authentication failed: username is null or empty");
        }

        // Set the userId field to the username
        income.setUserId(username);
        return incomeService.addIncome(income);
    }

    @GetMapping
    public Page<Income> getIncomes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        String authenticatedUserId = authentication.getName();
        return incomeService.getIncomesByUser(authenticatedUserId, page, size);
    }
}
