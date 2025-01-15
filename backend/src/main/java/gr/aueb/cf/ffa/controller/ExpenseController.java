package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    // Updated to include pagination
    @GetMapping
    public ResponseEntity<Page<Expense>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<Expense> expenses = expenseService.getExpensesByUser(authentication.getName(), page, size);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable String id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/all")
    public List<Expense> getAllExpenses(Authentication authentication) {
        String authenticatedUserId = authentication.getName();
        return expenseService.getAllExpensesByUser(authenticatedUserId);
    }
}