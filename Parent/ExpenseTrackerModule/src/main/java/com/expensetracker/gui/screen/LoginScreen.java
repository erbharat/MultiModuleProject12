package com.expensetracker.gui.screen;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.expensetracker.gui.controller.ScreenController;

public class LoginScreen extends Screen {

	private static LoginScreen loginScreen;

	public LoginScreen(ScreenController controller) {
		super(controller);
	}
	
	public URL getFXMLResource() {
		URL url = LoginScreen.class.getResource("LoginScreen.fxml");
		return url;
	}

	public static void initializeLoginScreen(ScreenController screenController) {
		if (loginScreen == null) {
			loginScreen = new LoginScreen(screenController);
		}	
	}
	
	public static LoginScreen getLoginScreen() {
		return loginScreen;
	}
}
