package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, Authentication authentication) {
        expense.setUserId(authentication.getName());
        return expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> getExpenses(Authentication authentication) {
        return expenseService.getExpensesByUser(authentication.getName());
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable String id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }
}