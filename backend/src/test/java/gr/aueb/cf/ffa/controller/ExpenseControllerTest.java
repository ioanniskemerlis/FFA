package gr.aueb.cf.ffa.controller;

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
    private Authentication authentication;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExpense_ShouldReturnCreatedExpense() {
        Expense expense = new Expense(null, "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent");
        when(authentication.getName()).thenReturn("user123");
        when(expenseService.addExpense(any(Expense.class))).thenReturn(expense);

        Expense result = expenseController.addExpense(expense, authentication);

        assertEquals(expense, result);
        verify(expenseService, times(1)).addExpense(any(Expense.class));
    }

    @Test
    void getExpenses_ShouldReturnPagedExpenses() {
        List<Expense> expenseList = List.of(
                new Expense("1", "user123", "Groceries", 100.0, LocalDate.now(), "Weekly groceries"),
                new Expense("2", "user123", "Utilities", 200.0, LocalDate.now(), "Monthly bill")
        );
        Page<Expense> expensePage = new PageImpl<>(expenseList);

        when(authentication.getName()).thenReturn("user123");
        when(expenseService.getExpensesByUser("user123", 0, 10)).thenReturn(expensePage);

        ResponseEntity<Page<Expense>> response = expenseController.getExpenses(0, 10, authentication);

        assertEquals(HttpStatusCode.valueOf(200) , response.getStatusCode());
        assertEquals(expensePage, response.getBody());
        verify(expenseService, times(1)).getExpensesByUser("user123", 0, 10);
    }

    @Test
    void updateExpense_ShouldReturnUpdatedExpense() {
        Expense updatedExpense = new Expense("1", "user123", "Groceries", 150.0, LocalDate.now(), "Updated groceries");
        when(expenseService.updateExpense(eq("1"), any(Expense.class))).thenReturn(updatedExpense);

        Expense result = expenseController.updateExpense("1", updatedExpense);

        assertEquals(updatedExpense, result);
        verify(expenseService, times(1)).updateExpense(eq("1"), any(Expense.class));
    }

    @Test
    void deleteExpense_ShouldCallService() {
        doNothing().when(expenseService).deleteExpense("1");

        expenseController.deleteExpense("1");

        verify(expenseService, times(1)).deleteExpense("1");
    }

    @Test
    void getAllExpenses_ShouldReturnAllExpensesForUser() {
        List<Expense> expenseList = List.of(
                new Expense("1", "user123", "Groceries", 100.0, LocalDate.now(), "Weekly groceries"),
                new Expense("2", "user123", "Utilities", 200.0, LocalDate.now(), "Monthly bill")
        );

        when(authentication.getName()).thenReturn("user123");
        when(expenseService.getAllExpensesByUser("user123")).thenReturn(expenseList);

        List<Expense> result = expenseController.getAllExpenses(authentication);

        assertEquals(expenseList, result);
        verify(expenseService, times(1)).getAllExpensesByUser("user123");
    }
}
