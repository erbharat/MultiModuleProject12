package com.expensetracker.gui.screen;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;

import com.expensetracker.gui.controller.ScreenController;

public class WelcomeScreen extends Screen {
	
	private static WelcomeScreen welcomeScreen = null;
	private static ScreenController controller = null;
	
	public WelcomeScreen(ScreenController controller) {
		super(controller);
	}

	public URL getFXMLResource() {
		URL url = WelcomeScreen.class.getResource("WelcomeScreen.fxml");
		return url;
	}

	public static void initializeWelcomeScreen(ScreenController screenController) {		
		if (welcomeScreen == null) {
			welcomeScreen = new WelcomeScreen(screenController);
			controller = screenController;
		}
	}
	
	public static WelcomeScreen getWelcomeScreen() {
		return welcomeScreen;
	}
	
	public static ScreenController getController() {
		return controller;
	}
}
