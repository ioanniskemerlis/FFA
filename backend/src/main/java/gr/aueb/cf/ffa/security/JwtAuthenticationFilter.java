package gr.aueb.cf.ffa.security;

import gr.aueb.cf.ffa.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A filter to handle JWT-based authentication.
 * This filter validates the JWT token from the `Authorization` header and populates the Spring Security context with authenticated user details.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * Constructor for injecting the {@link JwtUtil} dependency.
     *
     * @param jwtUtil Utility class for JWT operations such as validation and extraction.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Processes incoming HTTP requests to validate the JWT token and set up the Security Context if valid.
     *
     * @param request  Incoming HTTP request.
     * @param response Outgoing HTTP response.
     * @param chain    Filter chain for further request processing.
     * @throws ServletException If an error occurs during the filtering process.
     * @throws IOException      If an I/O error occurs during the filtering process.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Extract the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        // Validate and parse the JWT token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            String username = jwtUtil.extractUsername(token); // Extract username from the token

            // If username is extracted and no authentication exists in the Security Context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate the token
                if (jwtUtil.validateToken(token, username)) {
                    // Create an authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                    // Set the authentication in the Security Context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }
}
