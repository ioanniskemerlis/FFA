package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Expense;
import gr.aueb.cf.ffa.repository.ExpenseRepository;
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

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExpense_ShouldSaveExpense() {
        // Arrange
        Expense expense = new Expense(null, "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent payment");
        Expense savedExpense = new Expense("1", "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent payment");

        when(expenseRepository.save(any(Expense.class))).thenReturn(savedExpense);

        // Act
        Expense result = expenseService.addExpense(expense);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void getExpensesByUser_ShouldReturnPaginatedExpenses() {
        // Arrange
        String userId = "user123";
        PageRequest pageRequest = PageRequest.of(0, 10);
        Expense expense = new Expense("1", userId, "Rent", 1200.0, LocalDate.now(), "Monthly rent payment");
        Page<Expense> expensePage = new PageImpl<>(List.of(expense));

        when(expenseRepository.findByUserId(userId, pageRequest)).thenReturn(expensePage);

        // Act
        Page<Expense> result = expenseService.getExpensesByUser(userId, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(userId, result.getContent().get(0).getUserId());
        verify(expenseRepository, times(1)).findByUserId(userId, pageRequest);
    }

    @Test
    void updateExpense_ShouldUpdateExistingExpense() {
        // Arrange
        String expenseId = "1";
        Expense existingExpense = new Expense(expenseId, "user123", "Rent", 1200.0, LocalDate.now(), "Monthly rent payment");
        Expense updatedExpense = new Expense(null, "user123", "Groceries", 300.0, LocalDate.now(), "Weekly groceries");

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(existingExpense));
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Expense result = expenseService.updateExpense(expenseId, updatedExpense);

        // Assert
        assertNotNull(result);
        assertEquals("Groceries", result.getType());
        assertEquals(300.0, result.getAmount());
        verify(expenseRepository, times(1)).findById(expenseId);
        verify(expenseRepository, times(1)).save(existingExpense);
    }

    @Test
    void deleteExpense_ShouldDeleteExpenseById() {
        // Arrange
        String expenseId = "1";

        // Act
        expenseService.deleteExpense(expenseId);

        // Assert
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }

    @Test
    void getAllExpensesByUser_ShouldReturnAllExpensesForUser() {
        // Arrange
        String userId = "user123";
        Expense expense1 = new Expense("1", userId, "Rent", 1200.0, LocalDate.now(), "Monthly rent payment");
        Expense expense2 = new Expense("2", userId, "Groceries", 300.0, LocalDate.now(), "Weekly groceries");

        when(expenseRepository.findAllExpensesByUserId(userId)).thenReturn(List.of(expense1, expense2));

        // Act
        List<Expense> result = expenseService.getAllExpensesByUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findAllExpensesByUserId(userId);
    }
}
