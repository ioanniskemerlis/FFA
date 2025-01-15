package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Updated Service Layer
    public Page<Expense> getExpensesByUser(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepository.findByUserId(userId, pageable);
    }

    public Expense updateExpense(String id, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        existingExpense.setType(updatedExpense.getType());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setDate(updatedExpense.getDate());
        existingExpense.setNotes(updatedExpense.getNotes());
        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }
}