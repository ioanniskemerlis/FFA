package gr.aueb.cf.ffa.repository;

import gr.aueb.cf.ffa.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ExpenseRepository extends MongoRepository<Expense, String> {
    // Updated to return paginated results
    Page<Expense> findByUserId(String userId, Pageable pageable);

    List<Expense> findAllExpensesByUserId(String userId);
}