package com.expensetracker.gui.screen;

import java.net.URL;

import com.expensetracker.gui.controller.ScreenController;

public class DashboardScreen extends Screen {

	private static DashboardScreen dashboardScreen;
	public DashboardScreen(ScreenController controller) {
		super(controller);
	}

	public static void initializeDashboardScreen(ScreenController screenController) {		
		if (dashboardScreen == null) {
			dashboardScreen = new DashboardScreen(screenController);
		}
	}
	
	public static DashboardScreen getDashboardScreen() {
		return dashboardScreen;
	}
	public URL getFXMLResource() {
		URL url = DashboardScreen.class.getResource("DashboardScreen.fxml");
		return url;
	}
}
