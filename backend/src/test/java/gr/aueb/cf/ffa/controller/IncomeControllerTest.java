package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IncomeControllerTest {

    @Mock
    private IncomeService incomeService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private IncomeController incomeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addIncome_ShouldReturnCreatedIncome() {
        Income income = new Income(null, "user123", "Salary", 5000.0, LocalDate.now(), "Monthly salary");
        when(authentication.getName()).thenReturn("user123");
        when(incomeService.addIncome(any(Income.class))).thenReturn(income);

        Income result = incomeController.addIncome(income, authentication);

        assertEquals(income, result);
        verify(incomeService, times(1)).addIncome(any(Income.class));
    }

    @Test
    void getIncomes_ShouldReturnPagedIncomes() {
        List<Income> incomeList = List.of(
                new Income("1", "user123", "Freelancing", 1500.0, LocalDate.now(), "Project payment"),
                new Income("2", "user123", "Bonus", 500.0, LocalDate.now(), "Quarterly bonus")
        );
        Page<Income> incomePage = new PageImpl<>(incomeList);

        when(authentication.getName()).thenReturn("user123");
        when(incomeService.getIncomesByUser("user123", 0, 10)).thenReturn(incomePage);

        Page<Income> result = incomeController.getIncomes(0, 10, authentication);

        assertEquals(incomePage, result);
        verify(incomeService, times(1)).getIncomesByUser("user123", 0, 10);
    }

    @Test
    void getAllIncomes_ShouldReturnAllIncomesForUser() {
        List<Income> incomeList = List.of(
                new Income("1", "user123", "Freelancing", 1500.0, LocalDate.now(), "Project payment"),
                new Income("2", "user123", "Bonus", 500.0, LocalDate.now(), "Quarterly bonus")
        );

        when(authentication.getName()).thenReturn("user123");
        when(incomeService.getAllIncomesByUser("user123")).thenReturn(incomeList);

        List<Income> result = incomeController.getAllIncomes(authentication);

        assertEquals(incomeList, result);
        verify(incomeService, times(1)).getAllIncomesByUser("user123");
    }
}
