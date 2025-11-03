package com.healingtouch.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.healingtouch.model.User;
import com.healingtouch.service.AuthenticationService;
import com.healingtouch.service.PatientService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Controller for Login and Patient Registration view
 * Handles UI interactions and delegates business logic to service layer
 * 
 * @author Mauricio Belduque
 */
public class LoginController {

	// Services
	private AuthenticationService authService;
	private PatientService patientService;

	// Login fields
	@FXML
	private TextField txtFieldEmailLogin;

	@FXML
	private PasswordField pswdFieldPasswordLogin;

	// Registration fields
	@FXML
	private TextField txtFieldNames;

	@FXML
	private TextField txtFieldSurnames;

	@FXML
	private TextField txtFieldUserId;

	@FXML
	private TextField txtFieldPhone;

	@FXML
	private DatePicker dtPickerBirthdate;

	@FXML
	private TextField txtFieldAddress;

	@FXML
	private TextField txtFieldEmail;

	@FXML
	private PasswordField pswdFieldPassword;

	// Labels
	@FXML
	private Label lblErrorCredentials;

	@FXML
	private Label lblIdDuplicate;

	/**
	 * Constructor - initializes services
	 */
	public LoginController() {
		this.authService = new AuthenticationService();
		this.patientService = new PatientService();
	}

	/**
	 * Handle login action
	 */
	@FXML
	void login(MouseEvent event) {
		try {
			String email = txtFieldEmailLogin.getText();
			String password = pswdFieldPasswordLogin.getText();

			// Delegate authentication to service layer
			User user = authService.authenticate(email, password);

			if (user != null) {
				notifySuccessfulLogin();

				// Navigate to Patient module
				Parent root = FXMLLoader.load(getClass().getResource("/com/healingtouch/view/Patient.fxml"));
				Node node = (Node) event.getSource();
				Stage stagePatient = (Stage) node.getScene().getWindow();
				stagePatient.setScene(new Scene(root));
			} else {
				lblErrorCredentials.setText("Email o contraseña incorrecta");
				cleanPasswordField();
			}

		} catch (Exception e) {
			e.printStackTrace();
			lblErrorCredentials.setText("Error al iniciar sesión");
		}
	}

	/**
	 * Handle patient registration action
	 */
	@FXML
	void registerPatient(MouseEvent event) {
		try {
			String names = txtFieldNames.getText();
			String surnames = txtFieldSurnames.getText();
			String userId = txtFieldUserId.getText();
			String phone = txtFieldPhone.getText();
			LocalDate birthdate = dtPickerBirthdate.getValue();
			String address = txtFieldAddress.getText();
			String email = txtFieldEmail.getText();
			String password = pswdFieldPassword.getText();

			// Delegate registration to service layer
			String result = patientService.registerPatient(names, surnames, userId, phone, birthdate, address, email, password);

			switch (result) {
				case "SUCCESS":
					lblIdDuplicate.setText("");
					notifySuccessfulRecord();
					cleanRecordsFields();
					break;
				case "DOCUMENT_EXISTS":
					lblIdDuplicate.setText("Ya existe un paciente asociado a la cédula " + userId);
					cleanRecordsFields();
					break;
				case "EMAIL_EXISTS":
					lblIdDuplicate.setText("Ya existe un paciente asociado al email " + email);
					cleanRecordsFields();
					break;
				default:
					lblIdDuplicate.setText("Error al registrar paciente");
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			lblIdDuplicate.setText("Error al registrar paciente");
		}
	}

	/**
	 * Notify successful login
	 */
	private void notifySuccessfulLogin() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

		String title = "Bienvenido a Healingtouch";
		String message = hourdateFormat.format(date) + " " + txtFieldEmailLogin.getText();
		NotificationType notification = NotificationType.SUCCESS;

		TrayNotification tray = new TrayNotification(title, message, notification);
		tray.setRectangleFill(Paint.valueOf("#4d1d4d"));
		tray.showAndDismiss(Duration.seconds(5));
	}

	/**
	 * Clear password field
	 */
	private void cleanPasswordField() {
		pswdFieldPasswordLogin.setText(null);
	}

	/**
	 * Notify successful registration
	 */
	private void notifySuccessfulRecord() {
		String title = "Paciente registrado con éxito";
		String message = "Bienvenido(a) a Healingtouch " + txtFieldNames.getText() + " " + txtFieldSurnames.getText();
		NotificationType notification = NotificationType.SUCCESS;

		TrayNotification tray = new TrayNotification(title, message, notification);
		tray.setRectangleFill(Paint.valueOf("#4d1d4d"));
		tray.showAndDismiss(Duration.seconds(5));
	}

	/**
	 * Clear registration form fields
	 */
	private void cleanRecordsFields() {
		txtFieldNames.setText("");
		txtFieldSurnames.setText("");
		txtFieldUserId.setText("");
		txtFieldPhone.setText("");
		dtPickerBirthdate.setValue(null);
		txtFieldAddress.setText("");
		txtFieldEmail.setText("");
		pswdFieldPassword.setText("");
	}

	/**
	 * Close window action
	 */
	@FXML
	void close(MouseEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	// Window dragging support
	private double x = 0;
	private double y = 0;

	@FXML
	void pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	void dragged(MouseEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}
}
