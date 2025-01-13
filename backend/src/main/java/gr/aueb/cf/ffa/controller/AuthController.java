package gr.aueb.cf.ffa.controller;


import gr.aueb.cf.ffa.model.User;
import gr.aueb.cf.ffa.repository.UserRepository;
import gr.aueb.cf.ffa.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Registers a new user.
     *
     * @param user The user details.
     * @return A success message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Registration logic
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON) // Set Content-Type to JSON
                .body(response); // Return response as JSON
    }

    /**
     * Handles user login and issues a JWT token.
     *
     * @param user The login request containing username and password.
     * @return A JWT token if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON) // Explicitly set the content type
                    .body(response); // Return the token in JSON format
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    /**
     * Test endpoint to verify JWT authentication.
     */
    @GetMapping("/test")
    public String test() {
        return "JWT is working!";
    }
}
