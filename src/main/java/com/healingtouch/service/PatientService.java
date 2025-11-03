package com.healingtouch.service;

import java.time.LocalDate;

import com.healingtouch.model.User;
import com.healingtouch.repository.PatientRepository;
import com.healingtouch.repository.UserRepository;

/**
 * Service layer for patient operations
 * Contains business logic for patient registration and management
 * 
 * @author Mauricio Belduque
 */
public class PatientService {

    private PatientRepository patientRepository;
    private UserRepository userRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
        this.userRepository = new UserRepository();
    }

    /**
     * Register a new patient with user credentials
     * 
     * @param names patient names
     * @param surnames patient surnames
     * @param documentId patient document ID
     * @param phone patient phone
     * @param birthdate patient birthdate
     * @param address patient address
     * @param email user email
     * @param password user password
     * @return result message: "SUCCESS", "DOCUMENT_EXISTS", or "EMAIL_EXISTS"
     */
    public String registerPatient(String names, String surnames, String documentId, String phone,
                                   LocalDate birthdate, String address, String email, String password) {
        
        // Validate input
        if (names == null || surnames == null || documentId == null || email == null || password == null) {
            return "INVALID_DATA";
        }

        // Check if patient already exists
        if (patientRepository.existsByDocumentId(documentId)) {
            return "DOCUMENT_EXISTS";
        }

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            return "EMAIL_EXISTS";
        }

        // Save patient
        boolean patientSaved = patientRepository.save(names, surnames, documentId, phone, birthdate, address);
        
        if (!patientSaved) {
            return "PATIENT_SAVE_FAILED";
        }

        // Save user credentials
        User user = new User(email, password);
        boolean userSaved = userRepository.save(user);

        if (!userSaved) {
            return "USER_SAVE_FAILED";
        }

        return "SUCCESS";
    }
}
