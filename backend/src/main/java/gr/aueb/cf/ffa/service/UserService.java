package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.User;
import gr.aueb.cf.ffa.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing user-related operations.
 * Provides methods for user registration with secure password handling.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an instance of UserService with the specified DAO and password encoder.
     *
     * @param userRepository  The DAO used to interact with the users collection in MongoDB.
     * @param passwordEncoder The password encoder used for hashing user passwords.
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user by saving their details to the database.
     * The password is securely hashed before storage.
     *
     * @param user The user object containing registration details.
     *             Must include a unique username, email, and password.
     * @return The saved user object, including the generated ID and hashed password.
     */
    public User registerUser(User user) {
        // Hash the user's password using the provided password encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the user to the database
        return userRepository.save(user);
    }
}
