package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.Income;
import gr.aueb.cf.ffa.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    // Add a new income
    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    // Get all incomes for a user
    public List<Income> getIncomesByUser(String userId) {
        List<Income> incomes = incomeRepository.findByUserId(userId);
        if (incomes.isEmpty()) {
            System.out.println("No incomes found for userId: " + userId);
        }
        return incomes;
    }

    // Update an income
    public Income updateIncome(String id, Income updatedIncome) {
        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        existingIncome.setType(updatedIncome.getType());
        existingIncome.setAmount(updatedIncome.getAmount());
        existingIncome.setDate(updatedIncome.getDate());
        existingIncome.setNotes(updatedIncome.getNotes());
        return incomeRepository.save(existingIncome);
    }

    // Delete an income
    public void deleteIncome(String id) {
        incomeRepository.deleteById(id);
    }
}