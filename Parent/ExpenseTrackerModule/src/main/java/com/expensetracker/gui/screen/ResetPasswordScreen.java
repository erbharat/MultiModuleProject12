package com.expensetracker.gui.screen;

import java.net.URL;

import com.expensetracker.gui.controller.ScreenController;

public class ResetPasswordScreen extends Screen{
	
	private static ResetPasswordScreen resetPasswordScreen;
	public ResetPasswordScreen(ScreenController controller) {
		super(controller);
	}

	public static void initializeResetPasswordScreen(ScreenController screenController) {		
		if (resetPasswordScreen == null) {
			resetPasswordScreen = new ResetPasswordScreen(screenController);
		}
	}
	
	public static ResetPasswordScreen getResetPasswordScreen() {
		return resetPasswordScreen;
	}
	public URL getFXMLResource() {
		URL url = ResetPasswordScreen.class.getResource("ResetPassword.fxml");
		return url;
	}
}
