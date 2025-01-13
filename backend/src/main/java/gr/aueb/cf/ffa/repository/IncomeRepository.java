package gr.aueb.cf.ffa.repository;


import gr.aueb.cf.ffa.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncomeRepository extends MongoRepository<Income, String> {
    // Fetch all incomes for a specific user
    List<Income> findByUserId(String userId);
}