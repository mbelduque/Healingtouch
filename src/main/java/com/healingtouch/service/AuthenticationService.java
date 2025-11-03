package com.healingtouch.service;

import com.healingtouch.model.User;
import com.healingtouch.repository.UserRepository;

/**
 * Service layer for authentication operations
 * Contains business logic for user authentication
 * 
 * @author Mauricio Belduque
 */
public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService() {
        this.userRepository = new UserRepository();
    }

    /**
     * Authenticate user with email and password
     * 
     * @param email user email
     * @param password user password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String email, String password) {
        // Input validation
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }

        // Delegate to repository for database access
        return userRepository.findByEmailAndPassword(email, password);
    }

    /**
     * Check if email already exists
     * 
     * @param email email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
