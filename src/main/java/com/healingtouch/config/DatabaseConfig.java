package com.healingtouch.config;

import java.sql.Connection;

import com.healingtouch.util.JDBCConnectionPool;

/**
 * Singleton configuration class for database connection management
 * Centralizes database configuration and connection pooling
 * 
 * @author Mauricio Belduque
 */
public class DatabaseConfig {

    private static DatabaseConfig instance;
    private JDBCConnectionPool connectionPool;

    // Database configuration
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/healingtouchdb?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Private constructor to enforce singleton pattern
     */
    private DatabaseConfig() {
        connectionPool = new JDBCConnectionPool(DRIVER, URL, USER, PASSWORD);
    }

    /**
     * Get singleton instance of DatabaseConfig
     * 
     * @return DatabaseConfig instance
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    /**
     * Get a connection from the pool
     * 
     * @return Connection object
     */
    public Connection getConnection() {
        return connectionPool.checkOut();
    }

    /**
     * Release connection back to the pool
     * 
     * @param connection Connection to release
     */
    public void releaseConnection(Connection connection) {
        connectionPool.checkIn(connection);
    }
}
