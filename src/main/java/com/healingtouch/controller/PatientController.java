package com.healingtouch.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PatientController implements Initializable {
	@FXML
	private Pane pnl_book_appointment;

	@FXML
	private Pane pnl_search_appointment;

	@FXML
	private Pane pnl_settings;

	@FXML
	private Pane pnl_whoweare;

	@FXML
	private JFXButton btnMenu_book_appointment;

	@FXML
	private JFXButton btnMenu_search_appointment;

	@FXML
	private JFXButton btnMenu_settings;

	@FXML
	private JFXButton btnMenu_whoweare;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource() == btnMenu_book_appointment) {
			pnl_book_appointment.toFront();
		} else if (event.getSource() == btnMenu_search_appointment) {
			pnl_search_appointment.toFront();
		} else if (event.getSource() == btnMenu_settings) {
			pnl_settings.toFront();
		} else if (event.getSource() == btnMenu_whoweare) {
			pnl_whoweare.toFront();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void min(MouseEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setIconified(true);
	}
}
