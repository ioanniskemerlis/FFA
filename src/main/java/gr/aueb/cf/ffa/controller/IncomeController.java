package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
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
    public List<Income> getIncomes(Authentication authentication) {
        String username = authentication.getName();
        System.out.println("Fetching incomes for userId (username): " + username);

        return incomeService.getIncomesByUser(username);
    }
}
