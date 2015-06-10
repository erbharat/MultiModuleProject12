package com.expensetracker.gui.controller;

import javax.mail.internet.InternetAddress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.screen.LoginScreen;
import com.expensetracker.gui.screen.ScreenConstant;
import com.expensetracker.gui.screen.WelcomeScreen;
import com.expensetracker.gui.util.ControllerUtil;
import com.expensetracker.handler.Mail;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.UserManager;

public class ProfileScreenController {

	private static final int PASSWORD_MIN_LIMIT = 6;
	private static final int PASSWORD_MAX_LIMIT = 12;
	private static final int FIELD_MAX_LIMIT = 15;

	@FXML
	private Label passwordErrorLabel, cnfmPasswordErrorLabel;

	@FXML
	private TextField firstNameText, lastNameText, usernameText, passwordText,
			confirmPasswordText, emailIdText, mobileNumberText;

	@FXML
	private Button saveBtn, cancelBtn;

	public ProfileScreenController() {
	}

	@FXML
	public void save(ActionEvent event) {
		passwordErrorLabel.setText("");
		cnfmPasswordErrorLabel.setText("");
		Logger.logMessage("ProfileScreenController.save(.) : Saving new profile.");
		if (validateInput()) {

			User userProfile = createUserProfile();
			if (userProfile != null) {

				Logger.logMessage("ProfileScreenController.save(.) : User Profile created successfully : "
						+ userProfile);

				if (UserManager.getUserManager().addUser(userProfile) == Constants.JOB_PROCESSED_SUCCESSFULLY) {
					PopupWindow.popup
							.createInformationalPopup(
									"Success",
									"Congratulations "
											+ userProfile.getFirstname()
											+ " you have successfully created your profile!!");
					Mail.sendMail(
							userProfile.getEmailId(),
							"Congratulations "
									+ userProfile.getFirstname()
									+ "You have successfully created profile.\n Username - "
									+ userProfile.getUserName()
									+ "\nPassword - "
									+ userProfile.getPassword());
				}
				WelcomeScreen.getController().loadScreen(
						ScreenConstant.LOGIN_SCREEN,
						LoginScreen.getLoginScreen().getFXMLResource());
				WelcomeScreenController welcomeController = ((WelcomeScreenController) ControllerUtil.controllerUtil
						.getController(ScreenConstant.WELCOME_SCREEN));
				welcomeController.getSignUpBtn().setDisable(false);
				clear();

			}
		}
	}

	private boolean validateInput() {
		boolean retval = false;
		String errorMessage = "";

		Logger.logMessage("ProfileScreenController.validateInput() : Validating input...");

		if (firstNameText.getText() == null
				|| firstNameText.getText().length() == 0
				|| firstNameText.getText().length() > FIELD_MAX_LIMIT) {
			firstNameText
					.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
			errorMessage += "No valid first name!\n";
		} else {
			firstNameText.setStyle("");
		}
		if (lastNameText.getText() == null
				|| lastNameText.getText().length() == 0
				|| lastNameText.getText().length() > FIELD_MAX_LIMIT) {
			lastNameText
					.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
			errorMessage += "No valid last name!\n";
		} else {
			lastNameText.setStyle("");
		}

		if (usernameText.getText() == null
				|| usernameText.getText().length() == 0
				|| usernameText.getText().length() > FIELD_MAX_LIMIT) {
			usernameText
					.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
			errorMessage += "No valid username!\n";
		} else {
			usernameText.setStyle("");
		}
		if (passwordText.getText() != null
				&& passwordText.getText().length() < ProfileScreenController.PASSWORD_MIN_LIMIT
				|| passwordText.getText().length() > ProfileScreenController.PASSWORD_MAX_LIMIT) {
			passwordText
					.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
			passwordErrorLabel.setText("Min length 6 and max 15");
			errorMessage += "No valid Password!\n";
		} else {
			passwordText.setStyle("");
		}
		if (!validatePassword()) {
			errorMessage += "Password mismatch error!\n";
		}
		if (emailIdText.getText() == null
				|| emailIdText.getText().length() == 0
				|| !validateEmail(emailIdText.getText())) {
			emailIdText
					.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
			errorMessage += "No valid emailId!\n";
		} else {
			emailIdText.setStyle("");
		}
		if (mobileNumberText.getText() == null
				|| mobileNumberText.getText().length() != 10) {
			mobileNumberText
					.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
			errorMessage += "No valid mobile number (must be 10 digit number)!\n";
		} else {
			// try to parse the postal code into an int
			try {
				Long.parseLong(mobileNumberText.getText());
				mobileNumberText.setStyle("");
			} catch (NumberFormatException e) {
				mobileNumberText
						.setStyle("-fx-text-box-border: red ;  -fx-focus-color: red ;");
				errorMessage += "No valid mobile number (must be 10 digit number)!\n";
			}
		}

		if (errorMessage.length() == 0) {
			retval = true;
		} else {
			// Show the error message
			PopupWindow.popup.createErrorPopup("Errors in profile ",
					errorMessage);
			retval = false;
		}

		Logger.logMessage("ProfileScreenController.validateInput() : Validation input errors --> "
				+ errorMessage);

		return retval;
	}

	private boolean validateEmail(String emailId) {
		// TODO Auto-generated method stub
		boolean isValid = false;
		try {
			InternetAddress it = new InternetAddress(emailId);
			it.validate();
			isValid = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isValid;
	}

	private User createUserProfile() {

		String firstName = firstNameText.getText();
		String secondName = lastNameText.getText();
		String username = usernameText.getText();
		String password = passwordText.getText();
		String emailId = emailIdText.getText();
		String mobileNumber = mobileNumberText.getText();
		String empty = "";
		User userProfile = null;
		if (firstName.equals(empty) || secondName.equals(empty)
				|| username.equals(empty) || password.equals(empty)
				|| emailId.equals(empty) || mobileNumber.equals(empty)) {
			Logger.logMessage("ProfileScreenController.createUserProfile() : New Profile fields are empty");
		} else {

			userProfile = new User(firstName, secondName, username, password,
					emailId, mobileNumber, (byte) 0);
		}

		return userProfile;
	}

	private boolean validatePassword() {
		boolean valid = true;

		if (!passwordText.getText().equals(confirmPasswordText.getText())) {
			confirmPasswordText.clear();
			cnfmPasswordErrorLabel.setText("Password mismatch! Re-enter");
			valid = false;
		}
		return valid;
	}

	@FXML
	public void cancel(ActionEvent event) {
		Logger.logMessage("ProfileScreenController.cancel(.) : Resetting profile screen fields..");
		clear();
	}

	private void clear() {
		passwordErrorLabel.setText("");
		cnfmPasswordErrorLabel.setText("");
		firstNameText.clear();
		lastNameText.clear();
		usernameText.clear();
		passwordText.clear();
		confirmPasswordText.clear();
		emailIdText.clear();
		mobileNumberText.clear();
	}
}
