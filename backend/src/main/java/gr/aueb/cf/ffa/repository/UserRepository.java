package gr.aueb.cf.ffa.repository;


import gr.aueb.cf.ffa.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * UserRepository is responsible for interacting with the MongoDB database
 * to perform CRUD operations for User entities.
 */

public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a User by their username.
     *
     * @param username The username of the User to find.
     * @return An Optional containing the User if found, or empty if not.
     */
    Optional<User> findByUsername(String username);

}