package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.dao.IncomeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addIncome_ShouldSaveIncome() {
        // Arrange
        Income income = new Income(null, "user123", "Salary", 1000.0, LocalDate.now(), "Monthly Salary");
        Income savedIncome = new Income("1", "user123", "Salary", 1000.0, LocalDate.now(), "Monthly Salary");

        when(incomeRepository.save(any(Income.class))).thenReturn(savedIncome);

        // Act
        Income result = incomeService.addIncome(income);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(incomeRepository, times(1)).save(income);
    }

    @Test
    void getIncomesByUser_ShouldReturnPaginatedIncomes() {
        // Arrange
        String userId = "user123";
        PageRequest pageRequest = PageRequest.of(0, 10);
        Income income = new Income("1", userId, "Salary", 1000.0, LocalDate.now(), "Monthly Salary");
        Page<Income> incomePage = new PageImpl<>(List.of(income));

        when(incomeRepository.findByUserId(userId, pageRequest)).thenReturn(incomePage);

        // Act
        Page<Income> result = incomeService.getIncomesByUser(userId, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(userId, result.getContent().get(0).getUserId());
        verify(incomeRepository, times(1)).findByUserId(userId, pageRequest);
    }

    @Test
    void updateIncome_ShouldUpdateExistingIncome() {
        // Arrange
        String incomeId = "1";
        Income existingIncome = new Income(incomeId, "user123", "Salary", 1000.0, LocalDate.now(), "Monthly Salary");
        Income updatedIncome = new Income(null, "user123", "Bonus", 500.0, LocalDate.now(), "Performance Bonus");

        when(incomeRepository.findById(incomeId)).thenReturn(Optional.of(existingIncome));
        when(incomeRepository.save(any(Income.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Income result = incomeService.updateIncome(incomeId, updatedIncome);

        // Assert
        assertNotNull(result);
        assertEquals("Bonus", result.getType());
        assertEquals(500.0, result.getAmount());
        verify(incomeRepository, times(1)).findById(incomeId);
        verify(incomeRepository, times(1)).save(existingIncome);
    }

    @Test
    void deleteIncome_ShouldDeleteIncomeById() {
        // Arrange
        String incomeId = "1";

        // Act
        incomeService.deleteIncome(incomeId);

        // Assert
        verify(incomeRepository, times(1)).deleteById(incomeId);
    }

    @Test
    void getAllIncomesByUser_ShouldReturnAllIncomesForUser() {
        // Arrange
        String userId = "user123";
        Income income1 = new Income("1", userId, "Salary", 1000.0, LocalDate.now(), "Monthly Salary");
        Income income2 = new Income("2", userId, "Bonus", 500.0, LocalDate.now(), "Performance Bonus");

        when(incomeRepository.findAllIncomeByUserId(userId)).thenReturn(List.of(income1, income2));

        // Act
        List<Income> result = incomeService.getAllIncomesByUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(incomeRepository, times(1)).findAllIncomeByUserId(userId);
    }
}
