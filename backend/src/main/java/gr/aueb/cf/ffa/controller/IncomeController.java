package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * REST controller for managing incomes.
 * Provides endpoints for adding, retrieving, and managing incomes for authenticated users.
 */
@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    /**
     * Adds a new income for the authenticated user.
     *
     * @param income        The income object to be added.
     * @param authentication The authentication object containing user details.
     * @return The created income object.
     */
    @Operation(summary = "Add a new income", description = "Creates a new income record for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
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

    /**
     * Retrieves a paginated list of incomes for the authenticated user.
     *
     * @param page           The page number (0-based index, defaults to 0).
     * @param size           The number of items per page (defaults to 10).
     * @param authentication The authentication object containing user details.
     * @return A paginated list of incomes.
     */
    @Operation(summary = "Get paginated incomes", description = "Fetches a paginated list of incomes for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incomes retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping
    public Page<Income> getIncomes(
            @Parameter(description = "Page number, defaults to 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size, defaults to 10") @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        String authenticatedUserId = authentication.getName();
        return incomeService.getIncomesByUser(authenticatedUserId, page, size);
    }

    /**
     * Retrieves all incomes for the authenticated user.
     *
     * @param authentication The authentication object containing user details.
     * @return A list of all incomes for the user.
     */
    @Operation(summary = "Get all incomes", description = "Fetches all incomes for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incomes retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/all")
    public List<Income> getAllIncomes(Authentication authentication) {
        String authenticatedUserId = authentication.getName();
        return incomeService.getAllIncomesByUser(authenticatedUserId);
    }
}
