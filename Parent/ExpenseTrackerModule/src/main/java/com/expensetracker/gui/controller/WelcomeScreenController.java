package com.expensetracker.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.screen.LoginScreen;
import com.expensetracker.gui.screen.ProfileScreen;
import com.expensetracker.gui.screen.ScreenConstant;
import com.expensetracker.gui.screen.WelcomeScreen;
import com.expensetracker.gui.util.ControllerUtil;
import com.expensetracker.gui.util.ImageUtil;
import com.expensetracker.logger.Logger;

public class WelcomeScreenController implements Initializable {

	@FXML
	private Button signUpBtn, homeBtn, logoutBtn;

	/**
	 * @return the signUpBtn
	 */
	public Button getSignUpBtn() {
		return signUpBtn;
	}

	/**
	 * @return the homeBtn
	 */
	public Button getHomeBtn() {
		return homeBtn;
	}

	@FXML
	private MenuBar homeMenuBar, profileMenu, helpMenu;
	
	@FXML public StackPane screenSwitcher;

	@FXML
	public void signUp(ActionEvent event) {
		Logger.logMessage("WelcomeScreenController.signUp(.) : Loading profile screen for new profile... " + ScreenConstant.PROFILE_SCREEN);
		WelcomeScreen.getController().loadScreen(ScreenConstant.PROFILE_SCREEN,
				ProfileScreen.getProfileScreen().getFXMLResource());
		signUpBtn.setDisable(true);
	}

	@FXML
	public void login(ActionEvent event) {
		Logger.logMessage("WelcomeScreenController.login(.) : Trying to login switching to login screen " + ScreenConstant.LOGIN_SCREEN);
		if (signUpBtn.isDisabled()) {
			WelcomeScreen.getController().loadScreen(
					ScreenConstant.LOGIN_SCREEN,
					LoginScreen.getLoginScreen().getFXMLResource());
			signUpBtn.setDisable(false);
		} else {
			LoginScreenController loginController = ((LoginScreenController) ControllerUtil.controllerUtil
					.getController(ScreenConstant.LOGIN_SCREEN));
			
			if (loginController.signIn()) {

				Logger.logMessage("WelcomeScreenController.login(.) : Logged in successfully.");
				homeBtn.setVisible(false);
				signUpBtn.setVisible(false);
				logoutBtn.setVisible(true);
				DashboardScreenController dashboardController = ((DashboardScreenController) ControllerUtil.controllerUtil
						.getController(ScreenConstant.DASHBOARD_SCREEN));
				dashboardController.setLabel(loginController.getUsernameText_Home().getText());
				dashboardController.setMember(loginController.getUsernameText_Home().getText());
				dashboardController.getMemberTxt().setEditable(false);
				
			} else {
				
				Logger.logMessage("WelcomeScreenController.login(.) : Login failed....");
			}
		}
	}

	@FXML
	public void logout(ActionEvent event) {
		DashboardScreenController dashboardController = ((DashboardScreenController) ControllerUtil.controllerUtil
				.getController(ScreenConstant.DASHBOARD_SCREEN));
		Logger.logMessage("WelcomeScreenController.logout(.) : User " + dashboardController.getMemberTxt().getText() + " logged out from application" );
		WelcomeScreen.getController().loadScreen(ScreenConstant.LOGIN_SCREEN,
				LoginScreen.getLoginScreen().getFXMLResource());
		signUpBtn.setVisible(true);
		homeBtn.setVisible(true);
		logoutBtn.setVisible(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		/*homeBtn.setGraphic(new ImageView(ImageUtil.getLoginBtnPath()));
		logoutBtn.setGraphic(new ImageView(ImageUtil.getLogoutBtnPath()));*/
	}
}
