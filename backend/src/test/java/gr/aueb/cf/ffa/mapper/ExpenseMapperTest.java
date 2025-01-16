package gr.aueb.cf.ffa.mapper;

import gr.aueb.cf.ffa.dto.ExpenseRequestDTO;
import gr.aueb.cf.ffa.dto.ExpenseResponseDTO;
import gr.aueb.cf.ffa.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseMapperTest {

    private ExpenseMapper expenseMapper;

    @BeforeEach
    void setUp() {
        expenseMapper = new ExpenseMapper();
    }

    @Test
    void shouldMapExpenseRequestDTOToEntity() {
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO("Rent", 1200.0, LocalDate.of(2025, 1, 1), "Monthly rent");
        String userId = "user123";

        Expense result = expenseMapper.toEntity(requestDTO, userId);

        assertNull(result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(requestDTO.getType(), result.getType());
        assertEquals(requestDTO.getAmount(), result.getAmount());
        assertEquals(requestDTO.getDate(), result.getDate());
        assertEquals(requestDTO.getNotes(), result.getNotes());
    }

    @Test
    void shouldMapExpenseToResponseDTO() {
        Expense expense = new Expense("1", "user123", "Rent", 1200.0, LocalDate.of(2025, 1, 1), "Monthly rent");

        ExpenseResponseDTO result = expenseMapper.toResponseDTO(expense);

        assertEquals(expense.getId(), result.getId());
        assertEquals(expense.getUserId(), result.getUserId());
        assertEquals(expense.getType(), result.getType());
        assertEquals(expense.getAmount(), result.getAmount());
        assertEquals(expense.getDate(), result.getDate());
        assertEquals(expense.getNotes(), result.getNotes());
    }
}
