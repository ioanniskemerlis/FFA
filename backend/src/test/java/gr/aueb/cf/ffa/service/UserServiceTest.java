package gr.aueb.cf.ffa.service;

import gr.aueb.cf.ffa.model.User;
import gr.aueb.cf.ffa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldSaveUserWithHashedPassword() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plainpassword");
        user.setEmail("test@example.com");

        User savedUser = new User();
        savedUser.setId("12345");
        savedUser.setUsername("testuser");
        savedUser.setPassword("hashedpassword"); // Simulated hashed password
        savedUser.setEmail("test@example.com");

        when(passwordEncoder.encode("plainpassword")).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("hashedpassword", result.getPassword());
        assertEquals("test@example.com", result.getEmail());
        verify(passwordEncoder, times(1)).encode("plainpassword");
        verify(userRepository, times(1)).save(user);
    }
}
