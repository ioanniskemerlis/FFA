package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.User;
import gr.aueb.cf.ffa.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for loading user-specific data for Spring Security.
 * Implements the {@link UserDetailsService} interface to provide user authentication functionality.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs an instance of {@code CustomUserDetailsService} with the specified DAO.
     *
     * @param userRepository The DAO used to fetch user data from the MongoDB database.
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username for authentication and authorization purposes.
     *
     * @param username The username of the user to load.
     * @return A {@link UserDetails} object containing user credentials and authorities.
     * @throws UsernameNotFoundException if no user is found with the provided username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Build a Spring Security UserDetails object using the retrieved user data
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()) // Set the username
                .password(user.getPassword()) // Set the encoded password
                .roles(user.getRole()) // Assign roles (future expansion for admin roles)
                .build();
    }
}
