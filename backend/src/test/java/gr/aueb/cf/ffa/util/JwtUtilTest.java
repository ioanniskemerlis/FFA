package gr.aueb.cf.ffa.util;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String validUsername = "testUser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void shouldGenerateAndValidateTokenSuccessfully() {
        // Generate token
        String token = jwtUtil.generateToken(validUsername);

        // Validate token
        assertTrue(jwtUtil.validateToken(token, validUsername));
    }

    @Test
    void shouldExtractUsernameFromToken() {
        // Generate token
        String token = jwtUtil.generateToken(validUsername);

        // Extract username
        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(validUsername, extractedUsername);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        // Invalid token
        String invalidToken = "invalid.token.here";

        assertFalse(jwtUtil.validateToken(invalidToken, validUsername));
    }

    @Test
    void shouldThrowExceptionForMalformedToken() {
        // Malformed token
        String malformedToken = "malformed.token";

        // Validate token and expect exception
        assertThrows(JwtException.class, () -> jwtUtil.extractUsername(malformedToken));
    }

    @Test
    void shouldReturnFalseForMismatchedUsername() {
        // Generate token
        String token = jwtUtil.generateToken(validUsername);

        // Validate token with a different username
        assertFalse(jwtUtil.validateToken(token, "wrongUser"));
    }
}
