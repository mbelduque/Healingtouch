package com.healingtouch.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.healingtouch.helpers.JDBCConnectionPool;

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

public class LoginController {

	@FXML
	private TextField txtFieldEmailLogin;

	@FXML
	private PasswordField pswdFieldPasswordLogin;

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

	@FXML
	private Label lblErrorCredentials;

	@FXML
	private Label lblIdDuplicate;

	/**
	 * notifySuccessfulLogin() Notify that the login has been successful Try
	 * Notification Library
	 */
	public void notifySuccessfulLogin() {
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
	 * cleanPasswordField() clear the password field in the login
	 */
	public void cleanPasswordField() {
		pswdFieldPasswordLogin.setText(null);
	}

	/**
	 * notifySuccessfulRecord() Notifies that the registration has been successful
	 * Try Notification Library
	 */
	public void notifySuccessfulRecord() {
		String title = "Paciente registrado con éxito";
		String message = "Bienvenido(a) a Healingtouch " + txtFieldNames.getText() + " " + txtFieldSurnames.getText();
		NotificationType notification = NotificationType.SUCCESS;

		TrayNotification tray = new TrayNotification(title, message, notification);
		tray.setRectangleFill(Paint.valueOf("#4d1d4d"));
		tray.showAndDismiss(Duration.seconds(5));
	}

	/**
	 * cleanRecordsFields() clear the fields in the registration form
	 */
	public void cleanRecordsFields() {
		txtFieldNames.setText("");
		txtFieldSurnames.setText("");
		txtFieldUserId.setText("");
		txtFieldPhone.setText("");
		dtPickerBirthdate.setValue(null);
		txtFieldAddress.setText("");
		txtFieldEmail.setText("");
		pswdFieldPassword.setText("");
	}

	@FXML
	void login(MouseEvent event) {

		try {

			String email = txtFieldEmailLogin.getText();
			String password = pswdFieldPasswordLogin.getText();

			JDBCConnectionPool pool = new JDBCConnectionPool("com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/healingtouchdb?useSSL=false", "root", "");
			Connection connection = pool.checkOut();
			Statement statement = connection.createStatement();
			ResultSet status = statement.executeQuery(
					"SELECT * FROM user WHERE email" + " = '" + email + "' and password = '" + password + "'");

			if (status.next()) {
				notifySuccessfulLogin();

				// shows the patient module
				Parent root = FXMLLoader.load(getClass().getResource("/com/healingtouch/view/Patient.fxml"));
				Node node = (Node) event.getSource();
				Stage stagePatient = (Stage) node.getScene().getWindow();
				stagePatient.setScene(new Scene(root));
			} else {
				lblErrorCredentials.setText("Email o contraseña incorrecta");
				cleanPasswordField();
			}

			pool.checkIn(connection);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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

			JDBCConnectionPool pool = new JDBCConnectionPool("com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/healingtouchdb?useSSL=false", "root", "");
			Connection connection = pool.checkOut();
			Statement statement = connection.createStatement();
			ResultSet existentPatient = statement
					.executeQuery("SELECT * FROM patient WHERE document_id" + " = '" + userId + "'");

			if (existentPatient.next()) {
				lblIdDuplicate.setText("Ya existe un paciente asociado a la cédula " + txtFieldUserId.getText());
				cleanRecordsFields();
			} else {
				int patientStatus = statement.executeUpdate(
						"INSERT INTO patient (names, surnames, document_id, telephone, birthdate, address)"
								+ " values('" + names + "', '" + surnames + "', '" + userId + "', '" + phone + "', '"
								+ birthdate + "', '" + address + "')");

				ResultSet existentEmail = statement
						.executeQuery("SELECT * FROM user WHERE email" + " = '" + email + "'");

				if (existentEmail.next()) {
					lblIdDuplicate.setText("Ya existe un paciente asociado al email " + txtFieldEmail.getText());
					cleanRecordsFields();
				} else {
					int userStatus = statement.executeUpdate(
							"INSERT INTO user (email, password)" + " values('" + email + "', '" + password + "')");

					if (patientStatus > 0 && userStatus > 0) {
						lblIdDuplicate.setText("");
						notifySuccessfulRecord();
						cleanRecordsFields();
					}
				}

			}

			pool.checkIn(connection);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void close(MouseEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	// draggable pane
	double x = 0;
	double y = 0;

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
