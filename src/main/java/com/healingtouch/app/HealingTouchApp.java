package com.healingtouch.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main application class for HealingTouch
 * Entry point for the JavaFX application
 * 
 * @author Mauricio Belduque
 */
public class HealingTouchApp extends Application {

	public static Stage stage = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// Load the initial view (Manager/Login)
			Parent root = FXMLLoader.load(getClass().getResource("/com/healingtouch/view/Manager.fxml"));
			Scene scene = new Scene(root);
			
			// Configure stage
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setTitle("HealingTouch - Appointment Booking System");
			
			// Store stage reference for later use
			HealingTouchApp.stage = primaryStage;
			
			// Show the stage
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method - launches the JavaFX application
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
