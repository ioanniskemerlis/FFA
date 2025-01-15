package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    /**
     * Add a new income.
     *
     * @param income The income object to be added.
     * @return The saved Income object.
     */
    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    /**
     * Get all incomes for a user with pagination.
     *
     * @param userId The ID of the user.
     * @param page   The page number (0-based index).
     * @param size   The number of items per page.
     * @return A Page of Income objects.
     */
    public Page<Income> getIncomesByUser(String userId, int page, int size) {
        return incomeRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    /**
     * Update an income.
     *
     * @param id           The ID of the income to update.
     * @param updatedIncome The updated Income object.
     * @return The updated Income object.
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
     * Delete an income.
     *
     * @param id The ID of the income to delete.
     */
    public void deleteIncome(String id) {
        incomeRepository.deleteById(id);
    }
}