package com.expensetracker.gui.screen;

import java.net.URL;

import com.expensetracker.gui.controller.ScreenController;

public class ViewProfileScreen extends Screen{
	
	private static ViewProfileScreen viewProfileScreen;
	public ViewProfileScreen(ScreenController controller) {
		super(controller);
	}

	public static void initializeViewProfileScreen(ScreenController screenController) {		
		if (viewProfileScreen == null) {
			viewProfileScreen = new ViewProfileScreen(screenController);
		}
	}
	
	public static ViewProfileScreen getViewProfileScreen() {
		return viewProfileScreen;
	}
	public URL getFXMLResource() {
		URL url = ViewProfileScreen.class.getResource("ViewProfile.fxml");
		return url;
	}
}

