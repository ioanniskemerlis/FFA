package gr.aueb.cf.ffa.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "+o8B+HUKZVd+GUGkZaJYu7oanmXiKLkQi1g9wvs+wjE=";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username to include in the token.
     * @return A signed JWT token.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username.
     */
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates a JWT token.
     *
     * @param token    The JWT token.
     * @param username The username to validate.
     * @return True if valid, false otherwise.
     */
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token.
     * @return True if expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}