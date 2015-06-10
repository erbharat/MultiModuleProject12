package com.expensetracker.gui.screen;

import java.net.URL;

import com.expensetracker.gui.controller.ScreenController;

public class EditProfileScreen extends Screen {

	private static EditProfileScreen editProfileScreen;

	public EditProfileScreen(ScreenController controller) {
		super(controller);
	}

	public static void initializeEditProfileScreen(
			ScreenController screenController) {
		if (editProfileScreen == null) {
			editProfileScreen = new EditProfileScreen(screenController);
		}
	}

	public static EditProfileScreen getEditProfileScreen() {
		return editProfileScreen;
	}

	public URL getFXMLResource() {
		URL url = EditProfileScreen.class.getResource("EditProfile.fxml");
		return url;
	}
}
