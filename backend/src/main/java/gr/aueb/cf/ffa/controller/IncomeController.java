package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.dto.IncomeRequestDTO;
import gr.aueb.cf.ffa.dto.IncomeResponseDTO;
import gr.aueb.cf.ffa.mapper.IncomeMapper;
import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "http://localhost:4200")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private IncomeMapper incomeMapper;

    @PostMapping
    @Operation(summary = "Add a new income", description = "Creates a new income record for the authenticated user.")
    public IncomeResponseDTO addIncome(@RequestBody IncomeRequestDTO dto, Authentication authentication) {
        String username = authentication.getName();
        Income income = incomeMapper.toEntity(dto, username);
        return incomeMapper.toResponseDTO(incomeService.addIncome(income));
    }

    @GetMapping
    @Operation(summary = "Get paginated incomes", description = "Fetches a paginated list of incomes for the authenticated user.")
    public Page<IncomeResponseDTO> getIncomes(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              Authentication authentication) {
        String userId = authentication.getName();
        return incomeService.getIncomesByUser(userId, page, size)
                .map(incomeMapper::toResponseDTO);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all incomes", description = "Fetches all incomes for the authenticated user.")
    public List<IncomeResponseDTO> getAllIncomes(Authentication authentication) {
        String userId = authentication.getName();
        return incomeService.getAllIncomesByUser(userId).stream()
                .map(incomeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
