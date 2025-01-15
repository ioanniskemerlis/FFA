package gr.aueb.cf.ffa.repository;

import gr.aueb.cf.ffa.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ExpenseRepository extends MongoRepository<Expense, String> {
    // Updated to return paginated results
    Page<Expense> findByUserId(String userId, Pageable pageable);
}