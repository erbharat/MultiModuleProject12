package com.expensetracker.gui.popup;


import com.expensetracker.gui.controller.ScreenController;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupWindow {
	
	public static PopupWindow popup = new PopupWindow();
	
	public void createInformationalPopup(String headerMessage, String message) {		
		Popup.show(ScreenController.stage, message, headerMessage, Popup.OK );
		
	}
	
	public void createErrorPopup(String headerMessage, String message) {
		Popup.show(ScreenController.stage, message, headerMessage, Popup.ICON_ERROR );	
	}
	
	public void createExceptionPopup(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Look, an Information Dialog");
		alert.setContentText("I have a great message for you!");

		alert.showAndWait();	
	}
}
