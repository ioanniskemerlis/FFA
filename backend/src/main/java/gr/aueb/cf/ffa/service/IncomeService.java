package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.DAO.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing income-related operations.
 * Provides methods for adding, retrieving, updating, and deleting incomes.
 */
@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    /**
     * Constructs an instance of IncomeService with the specified DAO.
     *
     * @param incomeRepository The DAO used to interact with the incomes collection.
     */
    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    /**
     * Adds a new income to the database.
     *
     * @param income The income object to be saved.
     * @return The saved income object with its generated ID.
     */
    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    /**
     * Retrieves a paginated list of incomes for a specific user.
     *
     * @param userId The ID of the user whose incomes are being retrieved.
     * @param page   The page number (0-based index).
     * @param size   The number of records per page.
     * @return A Page of Income objects.
     */
    public Page<Income> getIncomesByUser(String userId, int page, int size) {
        return incomeRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    /**
     * Updates an existing income with new values.
     *
     * @param id            The ID of the income to update.
     * @param updatedIncome The updated income object with new values.
     * @return The updated income object after saving to the database.
     * @throws RuntimeException If the income with the specified ID is not found.
     */
    public Income updateIncome(String id, Income updatedIncome) {
        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        existingIncome.setType(updatedIncome.getType());
        existingIncome.setAmount(updatedIncome.getAmount());
        existingIncome.setDate(updatedIncome.getDate());
        existingIncome.setNotes(updatedIncome.getNotes());
        return incomeRepository.save(existingIncome);
    }

    /**
     * Deletes an income from the database by its ID.
     *
     * @param id The ID of the income to delete.
     */
    public void deleteIncome(String id) {
        incomeRepository.deleteById(id);
    }

    /**
     * Retrieves all incomes for a specific user.
     *
     * @param userId The ID of the user whose incomes are being retrieved.
     * @return A list of all Income objects for the specified user.
     */
    public List<Income> getAllIncomesByUser(String userId) {
        return incomeRepository.findAllIncomeByUserId(userId);
    }
}
