package gr.aueb.cf.ffa.repository;

import gr.aueb.cf.ffa.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUserId(String userId);
}