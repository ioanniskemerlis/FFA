package gr.aueb.cf.ffa.mapper;

import gr.aueb.cf.ffa.dto.IncomeRequestDTO;
import gr.aueb.cf.ffa.dto.IncomeResponseDTO;
import gr.aueb.cf.ffa.model.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IncomeMapperTest {

    private IncomeMapper incomeMapper;

    @BeforeEach
    void setUp() {
        incomeMapper = new IncomeMapper();
    }

    @Test
    void shouldMapIncomeRequestDTOToEntity() {
        IncomeRequestDTO requestDTO = new IncomeRequestDTO("Salary", 5000.0, LocalDate.of(2025, 1, 1), "Monthly salary");
        String userId = "user123";

        Income result = incomeMapper.toEntity(requestDTO, userId);

        assertNull(result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(requestDTO.getType(), result.getType());
        assertEquals(requestDTO.getAmount(), result.getAmount());
        assertEquals(requestDTO.getDate(), result.getDate());
        assertEquals(requestDTO.getNotes(), result.getNotes());
    }

    @Test
    void shouldMapIncomeToResponseDTO() {
        Income income = new Income("1", "user123", "Salary", 5000.0, LocalDate.of(2025, 1, 1), "Monthly salary");

        IncomeResponseDTO result = incomeMapper.toResponseDTO(income);

        assertEquals(income.getId(), result.getId());
        assertEquals(income.getUserId(), result.getUserId());
        assertEquals(income.getType(), result.getType());
        assertEquals(income.getAmount(), result.getAmount());
        assertEquals(income.getDate(), result.getDate());
        assertEquals(income.getNotes(), result.getNotes());
    }
}
