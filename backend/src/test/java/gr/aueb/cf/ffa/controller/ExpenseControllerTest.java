package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.dto.ExpenseRequestDTO;
import gr.aueb.cf.ffa.dto.ExpenseResponseDTO;
import gr.aueb.cf.ffa.mapper.ExpenseMapper;
import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private ExpenseMapper expenseMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExpense_ShouldReturnCreatedExpenseDTO() {
        // Mock input
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO("Rent", 1200.0, LocalDate.now(), "Monthly rent");
        Expense expense = new Expense(null, "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent");
        Expense savedExpense = new Expense("1", "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent");
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO("1", "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent");

        // Mock behaviors
        when(authentication.getName()).thenReturn("user123");
        when(expenseMapper.toEntity(requestDTO, "user123")).thenReturn(expense);
        when(expenseService.addExpense(expense)).thenReturn(savedExpense);
        when(expenseMapper.toResponseDTO(savedExpense)).thenReturn(responseDTO);

        // Perform test
        ExpenseResponseDTO result = expenseController.addExpense(requestDTO, authentication);

        // Verify and assert
        assertEquals(responseDTO, result);
        verify(expenseService, times(1)).addExpense(expense);
        verify(expenseMapper, times(1)).toEntity(requestDTO, "user123");
        verify(expenseMapper, times(1)).toResponseDTO(savedExpense);
    }

    @Test
    void getExpenses_ShouldReturnPaginatedExpensesDTO() {
        // Mock input
        List<Expense> expenses = List.of(
                new Expense("1", "user123", "Groceries", 100.0, LocalDate.now(), "Weekly groceries"),
                new Expense("2", "user123", "Utilities", 200.0, LocalDate.now(), "Monthly bill")
        );
        Page<Expense> expensePage = new PageImpl<>(expenses);

        List<ExpenseResponseDTO> responseDTOs = List.of(
                new ExpenseResponseDTO("1", "user123", "Groceries", 100.0, LocalDate.now(), "Weekly groceries"),
                new ExpenseResponseDTO("2", "user123", "Utilities", 200.0, LocalDate.now(), "Monthly bill")
        );
        Page<ExpenseResponseDTO> responsePage = new PageImpl<>(responseDTOs);

        // Mock behaviors
        when(authentication.getName()).thenReturn("user123");
        when(expenseService.getExpensesByUser("user123", 0, 10)).thenReturn(expensePage);
        when(expenseMapper.toResponseDTO(any(Expense.class))).thenAnswer(invocation -> {
            Expense expense = invocation.getArgument(0);
            return new ExpenseResponseDTO(expense.getId(), expense.getUserId(), expense.getType(),
                    expense.getAmount(), expense.getDate(), expense.getNotes());
        });

        // Perform test
        ResponseEntity<Page<ExpenseResponseDTO>> result = expenseController.getExpenses(0, 10, authentication);

        // Verify and assert
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
        assertEquals(responsePage.getContent().size(), result.getBody().getContent().size());
        verify(expenseService, times(1)).getExpensesByUser("user123", 0, 10);
    }

    @Test
    void updateExpense_ShouldReturnUpdatedExpenseDTO() {
        // Mock input
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO("Rent", 1300.0, LocalDate.now(), "Updated rent");
        Expense updatedExpense = new Expense("1", "user123", "Rent", 1300.0, LocalDate.now(), "Updated rent");
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO("1", "user123", "Rent", 1300.0, LocalDate.now(), "Updated rent");

        // Mock behaviors
        when(expenseMapper.toEntity(requestDTO, null)).thenReturn(updatedExpense);
        when(expenseService.updateExpense("1", updatedExpense)).thenReturn(updatedExpense);
        when(expenseMapper.toResponseDTO(updatedExpense)).thenReturn(responseDTO);

        // Perform test
        ExpenseResponseDTO result = expenseController.updateExpense("1", requestDTO);

        // Verify and assert
        assertEquals(responseDTO, result);
        verify(expenseService, times(1)).updateExpense("1", updatedExpense);
        verify(expenseMapper, times(1)).toEntity(requestDTO, null);
        verify(expenseMapper, times(1)).toResponseDTO(updatedExpense);
    }

    @Test
    void deleteExpense_ShouldCallService() {
        // Perform test
        expenseController.deleteExpense("1");

        // Verify
        verify(expenseService, times(1)).deleteExpense("1");
    }

    @Test
    void getAllExpenses_ShouldReturnAllExpensesDTO() {
        // Mock input
        List<Expense> expenses = List.of(
                new Expense("1", "user123", "Groceries", 100.0, LocalDate.now(), "Weekly groceries"),
                new Expense("2", "user123", "Utilities", 200.0, LocalDate.now(), "Monthly bill")
        );

        List<ExpenseResponseDTO> responseDTOs = List.of(
                new ExpenseResponseDTO("1", "user123", "Groceries", 100.0, LocalDate.now(), "Weekly groceries"),
                new ExpenseResponseDTO("2", "user123", "Utilities", 200.0, LocalDate.now(), "Monthly bill")
        );

        // Mock behaviors
        when(authentication.getName()).thenReturn("user123");
        when(expenseService.getAllExpensesByUser("user123")).thenReturn(expenses);
        when(expenseMapper.toResponseDTO(any(Expense.class))).thenAnswer(invocation -> {
            Expense expense = invocation.getArgument(0);
            return new ExpenseResponseDTO(expense.getId(), expense.getUserId(), expense.getType(),
                    expense.getAmount(), expense.getDate(), expense.getNotes());
        });

        // Perform test
        List<ExpenseResponseDTO> result = expenseController.getAllExpenses(authentication);

        // Verify and assert
        assertEquals(responseDTOs.size(), result.size());
        verify(expenseService, times(1)).getAllExpensesByUser("user123");
    }
}
