package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.dto.ExpenseRequestDTO;
import gr.aueb.cf.ffa.dto.ExpenseResponseDTO;
import gr.aueb.cf.ffa.mapper.ExpenseMapper;
import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing expenses with DTOs.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseMapper expenseMapper;

    /**
     * Adds a new expense for the authenticated user.
     *
     * @param expenseDTO     The DTO containing expense details.
     * @param authentication The authentication object.
     * @return The created expense as a DTO.
     */
    @Operation(summary = "Add a new expense")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ExpenseResponseDTO addExpense(@RequestBody ExpenseRequestDTO expenseDTO, Authentication authentication) {
        Expense expense = expenseMapper.toEntity(expenseDTO, authentication.getName());
        Expense savedExpense = expenseService.addExpense(expense);
        return expenseMapper.toResponseDTO(savedExpense);
    }

    /**
     * Retrieves paginated expenses for the authenticated user.
     *
     * @param page           The page number (0-based index).
     * @param size           The number of items per page.
     * @param authentication The authentication object.
     * @return A paginated list of expenses as DTOs.
     */
    @Operation(summary = "Get paginated expenses")
    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<Expense> expenses = expenseService.getExpensesByUser(authentication.getName(), page, size);
        Page<ExpenseResponseDTO> response = expenses.map(expenseMapper::toResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing expense.
     *
     * @param id          The ID of the expense to update.
     * @param expenseDTO  The DTO containing updated expense details.
     * @return The updated expense as a DTO.
     */
    @Operation(summary = "Update an expense")
    @PutMapping("/{id}")
    public ExpenseResponseDTO updateExpense(@PathVariable String id, @RequestBody ExpenseRequestDTO expenseDTO) {
        Expense updatedExpense = expenseMapper.toEntity(expenseDTO, null);
        updatedExpense.setId(id);
        return expenseMapper.toResponseDTO(expenseService.updateExpense(id, updatedExpense));
    }

    /**
     * Deletes an expense by its ID.
     *
     * @param id The ID of the expense to delete.
     */
    @Operation(summary = "Delete an expense")
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    /**
     * Retrieves all expenses for the authenticated user.
     *
     * @param authentication The authentication object.
     * @return A list of all expenses as DTOs.
     */
    @Operation(summary = "Get all expenses")
    @GetMapping("/all")
    public List<ExpenseResponseDTO> getAllExpenses(Authentication authentication) {
        return expenseService.getAllExpensesByUser(authentication.getName())
                .stream()
                .map(expenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
