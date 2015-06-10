package com.expensetracker.gui.screen;

import java.net.URL;

import com.expensetracker.gui.controller.ScreenController;

public class ProfileScreen extends Screen{

	private static ProfileScreen profileScreen;

	public ProfileScreen(ScreenController controller) {
		super(controller);
	}

	public URL getFXMLResource() {
		URL url = ProfileScreen.class.getResource("ProfileScreen.fxml");
		return url;
	}

	public static void initializeProfileScreen(ScreenController screenController) {		
		if (profileScreen == null) {
			profileScreen = new ProfileScreen(screenController);
		}
	}
	
	public static ProfileScreen getProfileScreen() {
		return profileScreen;
	}
	
}
