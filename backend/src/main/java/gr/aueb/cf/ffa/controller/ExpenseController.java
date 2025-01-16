package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing expenses.
 * Provides endpoints for adding, retrieving, updating, and deleting expenses.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    /**
     * Adds a new expense for the authenticated user.
     *
     * @param expense        The expense object to be added.
     * @param authentication The authentication object containing user details.
     * @return The created expense object.
     */
    @Operation(summary = "Add a new expense", description = "Creates a new expense for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, Authentication authentication) {
        expense.setUserId(authentication.getName());
        return expenseService.addExpense(expense);
    }

    /**
     * Retrieves a paginated list of expenses for the authenticated user.
     *
     * @param page           The page number (0-based index, defaults to 0).
     * @param size           The number of items per page (defaults to 10).
     * @param authentication The authentication object containing user details.
     * @return A paginated list of expenses.
     */
    @Operation(summary = "Get paginated expenses", description = "Fetches a paginated list of expenses for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping
    public ResponseEntity<Page<Expense>> getExpenses(
            @Parameter(description = "Page number, defaults to 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size, defaults to 10") @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<Expense> expenses = expenseService.getExpensesByUser(authentication.getName(), page, size);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Updates an existing expense for the authenticated user.
     *
     * @param id      The ID of the expense to update.
     * @param expense The updated expense object.
     * @return The updated expense object.
     */
    @Operation(summary = "Update an expense", description = "Updates an existing expense by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable String id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    /**
     * Deletes an expense by its ID for the authenticated user.
     *
     * @param id The ID of the expense to delete.
     */
    @Operation(summary = "Delete an expense", description = "Deletes an expense by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    /**
     * Retrieves all expenses for the authenticated user.
     *
     * @param authentication The authentication object containing user details.
     * @return A list of all expenses for the user.
     */
    @Operation(summary = "Get all expenses", description = "Fetches all expenses for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/all")
    public List<Expense> getAllExpenses(Authentication authentication) {
        String authenticatedUserId = authentication.getName();
        return expenseService.getAllExpensesByUser(authenticatedUserId);
    }
}
