package com.healingtouch.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import com.healingtouch.config.DatabaseConfig;
import com.healingtouch.model.Patient;

/**
 * Repository (DAO) for Patient entity
 * Handles all database operations related to patients
 * 
 * @author Mauricio Belduque
 */
public class PatientRepository {

    private DatabaseConfig dbConfig;

    public PatientRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    /**
     * Check if patient exists by document ID
     * 
     * @param documentId patient document ID
     * @return true if exists, false otherwise
     */
    public boolean existsByDocumentId(String documentId) {
        boolean exists = false;
        Connection connection = null;
        
        try {
            connection = dbConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM patient WHERE document_id = '" + documentId + "'");
            
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
     * Save a new patient
     * 
     * @param names patient names
     * @param surnames patient surnames
     * @param documentId patient document ID
     * @param phone patient phone
     * @param birthdate patient birthdate
     * @param address patient address
     * @return true if saved successfully, false otherwise
     */
    public boolean save(String names, String surnames, String documentId, String phone, LocalDate birthdate, String address) {
        Connection connection = null;
        
        try {
            connection = dbConfig.getConnection();
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(
                    "INSERT INTO patient (names, surnames, document_id, telephone, birthdate, address) " +
                    "VALUES ('" + names + "', '" + surnames + "', '" + documentId + "', '" + phone + "', '" + birthdate + "', '" + address + "')");
            
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
