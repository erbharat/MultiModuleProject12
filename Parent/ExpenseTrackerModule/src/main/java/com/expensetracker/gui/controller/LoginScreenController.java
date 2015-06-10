package com.expensetracker.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.expensetracker.gui.screen.DashboardScreen;
import com.expensetracker.gui.screen.ScreenConstant;
import com.expensetracker.gui.screen.WelcomeScreen;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.UserManager;

public class LoginScreenController {

	@FXML
	protected TextField usernameText_Home;

	/**
	 * @return the usernameText_Home
	 */
	public TextField getUsernameText_Home() {
		return usernameText_Home;
	}

	@FXML
	protected PasswordField passwordText_Home;

	@FXML
	private Button signInBtn, problemSignInBtn;

	@FXML
	public void signIn(ActionEvent event) {

		boolean isAuthenticated = UserManager.getUserManager()
				.authenticateUser(usernameText_Home.getText(),
						passwordText_Home.getText());
		if (isAuthenticated) {
			WelcomeScreen.getController().loadScreen(
					ScreenConstant.DASHBOARD_SCREEN,
					DashboardScreen.getDashboardScreen().getFXMLResource());
		}
	}

	public boolean signIn() {
		boolean isAuthenticated = false;

		Logger.logMessage("LoginScreenController.signIn() : Logging in using : Username - "
				+ usernameText_Home.getText() + " password - "
				+ passwordText_Home.getText());
		isAuthenticated = UserManager.getUserManager()
				.authenticateUser(usernameText_Home.getText(),
						passwordText_Home.getText());
		if (isAuthenticated) {
			WelcomeScreen.getController().loadScreen(
					ScreenConstant.DASHBOARD_SCREEN,
					DashboardScreen.getDashboardScreen().getFXMLResource());
		}
		
/*		  String username = "bharat"; String password = "password"; if
		 (usernameText_Home.getText().equals(username)) { if
		  (passwordText_Home.getText().equals(password)) {
		  WelcomeScreen.getController().loadScreen(
		  ScreenConstant.DASHBOARD_SCREEN,
		  DashboardScreen.getDashboardScreen().getFXMLResource());
		  isAuthenticated = true; } }*/
		 
		return isAuthenticated;
	}

	@FXML
	public void forGotPassword(ActionEvent event) {
		
		// TODO
		/*
		 * String username = "bharat"; String password = "password"; if
		 * (usernameText_Home.getText().equals(username)) { if
		 * (passwordText_Home.getText().equals(password)) {
		 * WelcomeScreen.getController
		 * ().loadScreen(LoginScreen.getLoginScreen().getFXMLResource()); } }
		 */
	}
}
