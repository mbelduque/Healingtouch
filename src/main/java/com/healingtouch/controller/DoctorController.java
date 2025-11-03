package com.healingtouch.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DoctorController implements Initializable {
	@FXML
	private Pane pnl_settings;

	@FXML
	private Pane pnl_manage_service;

	@FXML
	private Pane pnl_whoweare;

	@FXML
	private Pane pnl_manage_appointment;

	@FXML
	private Pane pnl_attend_appointment;

	@FXML
	private ToggleGroup asist;

	@FXML
	private JFXButton btnMenu_attend_appointment;

	@FXML
	private JFXButton btnMenu_manage_appointment;

	@FXML
	private JFXButton btnMenu_manage_service;

	@FXML
	private JFXButton btnMenu_settings;

	@FXML
	private JFXButton btnMenu_whoweare;

	@FXML
	void handleButtonAction(ActionEvent event) {
		if (event.getSource() == btnMenu_attend_appointment) {
			pnl_attend_appointment.toFront();
		} else if (event.getSource() == btnMenu_manage_appointment) {
			pnl_manage_appointment.toFront();
		} else if (event.getSource() == btnMenu_manage_service) {
			pnl_manage_service.toFront();
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

	@FXML
	void max(MouseEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setFullScreen(true);
	}
}
