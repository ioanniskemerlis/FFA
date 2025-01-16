package gr.aueb.cf.ffa.controller;

import gr.aueb.cf.ffa.model.User;
import gr.aueb.cf.ffa.repository.UserRepository;
import gr.aueb.cf.ffa.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldRegisterUserSuccessfully() {
        User user = new User("1", "testuser", "Testuser1$","john@hotmail.com", "USER");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = authController.register(user);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put("message", "User registered successfully!");
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    void register_ShouldReturnErrorWhenUsernameExists() {
        User user = new User("1", "testuser", "Testuser1$","john@hotmail.com", "USER");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.register(user);

        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put("message", "Username already exists!");
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    void login_ShouldReturnJwtTokenForValidCredentials() {
        User user = new User("1", "testuser", "Testuser1$","john@hotmail.com", "USER");
        String token = "jwt-token";

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn(token);

        ResponseEntity<?> response = authController.login(user);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put("token", token);
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    void login_ShouldReturnUnauthorizedForInvalidCredentials() {
        User user = new User("1", "testuser", "Testuser1$","john@hotmail.com", "USER");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(false);

        ResponseEntity<?> response = authController.login(user);

        assertEquals(HttpStatusCode.valueOf(401), response.getStatusCode());
        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put("message", "Invalid username or password");
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    void testEndpoint_ShouldReturnSuccessMessage() {
        String response = authController.test();

        assertEquals("JWT is working!", response);
    }
}
