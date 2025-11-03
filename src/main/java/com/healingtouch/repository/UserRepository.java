package com.healingtouch.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.healingtouch.config.DatabaseConfig;
import com.healingtouch.model.User;

/**
 * Repository (DAO) for User entity
 * Handles all database operations related to users
 * 
 * @author Mauricio Belduque
 */
public class UserRepository {

    private DatabaseConfig dbConfig;

    public UserRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    /**
     * Find user by email and password (for authentication)
     * 
     * @param email user email
     * @param password user password
     * @return User object if found, null otherwise
     */
    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        Connection connection = null;
        
        try {
            connection = dbConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'");

            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
            
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                dbConfig.releaseConnection(connection);
            }
        }
        
        return user;
    }

    /**
     * Check if user exists by email
     * 
     * @param email user email
     * @return true if exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        boolean exists = false;
        Connection connection = null;
        
        try {
            connection = dbConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE email = '" + email + "'");
            
            exists = rs.next();
            
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                dbConfig.releaseConnection(connection);
            }
        }
        
        return exists;
    }

    /**
     * Save a new user
     * 
     * @param user User object to save
     * @return true if saved successfully, false otherwise
     */
    public boolean save(User user) {
        Connection connection = null;
        
        try {
            connection = dbConfig.getConnection();
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(
                    "INSERT INTO user (email, password) VALUES ('" + user.getEmail() + "', '" + user.getPassword() + "')");
            
            statement.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                dbConfig.releaseConnection(connection);
            }
        }
    }
}
