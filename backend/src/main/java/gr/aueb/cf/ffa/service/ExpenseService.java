package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.dao.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing expense-related operations.
 * Provides methods for adding, retrieving, updating, and deleting expenses.
 */
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    /**
     * Constructs an instance of ExpenseService with the specified dao.
     *
     * @param expenseRepository The dao used to interact with the expenses collection.
     */
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Adds a new expense to the database.
     *
     * @param expense The expense object to be saved.
     * @return The saved expense object with its generated ID.
     */
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    /**
     * Retrieves a paginated list of expenses for a specific user.
     *
     * @param userId The ID of the user whose expenses are being retrieved.
     * @param page   The page number (0-based index).
     * @param size   The number of records per page.
     * @return A Page of Expense objects.
     */
    public Page<Expense> getExpensesByUser(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepository.findByUserId(userId, pageable);
    }

    /**
     * Updates an existing expense with new values.
     *
     * @param id            The ID of the expense to update.
     * @param updatedExpense The updated expense object with new values.
     * @return The updated expense object after saving to the database.
     * @throws RuntimeException If the expense with the specified ID is not found.
     */
    public Expense updateExpense(String id, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        existingExpense.setType(updatedExpense.getType());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setDate(updatedExpense.getDate());
        existingExpense.setNotes(updatedExpense.getNotes());
        return expenseRepository.save(existingExpense);
    }

    /**
     * Deletes an expense from the database by its ID.
     *
     * @param id The ID of the expense to delete.
     */
    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    /**
     * Retrieves all expenses for a specific user.
     *
     * @param userId The ID of the user whose expenses are being retrieved.
     * @return A list of all Expense objects for the specified user.
     */
    public List<Expense> getAllExpensesByUser(String userId) {
        return expenseRepository.findAllExpensesByUserId(userId);
    }
}
