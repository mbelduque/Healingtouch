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

public class ManagerController implements Initializable {
	@FXML
	private Pane pnl_manage_profesionals;

	@FXML
	private ToggleGroup asist;

	@FXML
	private Pane pnl_manage_medical_centers;

	@FXML
	private Pane pnl_manage_services;

	@FXML
	private Pane pnl_manage_appointments;

	@FXML
	private Pane pnl_manage_patients;

	@FXML
	private JFXButton btnMenu_patients;

	@FXML
	private JFXButton btnMenu_profesionals;

	@FXML
	private JFXButton btnMenu_medical_centers;

	@FXML
	private JFXButton btnMenu_specialitys;

	@FXML
	private JFXButton btnMenu_appointments;

	@FXML
	private JFXButton btnMenu_services;

	@FXML
	private JFXButton btnMenu_reports;
	
	@FXML
	void handleButtonAction(ActionEvent event) {
		if (event.getSource() == btnMenu_patients) {
			pnl_manage_patients.toFront();
		} else if (event.getSource() == btnMenu_profesionals) {
			pnl_manage_profesionals.toFront();
		} else if (event.getSource() == btnMenu_medical_centers) {
			pnl_manage_medical_centers.toFront();
		} else if (event.getSource() == btnMenu_specialitys) {
			pnl_manage_appointments.toFront();
		} else if (event.getSource() == btnMenu_services) {
			pnl_manage_services.toFront();
		} else if (event.getSource() == btnMenu_appointments) {
			pnl_manage_appointments.toFront();
		} else if (event.getSource() == btnMenu_reports) {
			pnl_manage_medical_centers.toFront();
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
