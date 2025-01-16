package gr.aueb.cf.ffa.DAO;

import gr.aueb.cf.ffa.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IncomeRepository extends MongoRepository<Income, String> {
    /**
     * Fetch all incomes for a specific user with pagination.
     *
     * @param userId   The ID of the user.
     * @param pageable The pagination details (page number and size).
     * @return A Page of Income objects.
     */
    Page<Income> findByUserId(String userId, Pageable pageable);

    // Fetch all incomes for a user
    List<Income> findAllIncomeByUserId(String userId);
}