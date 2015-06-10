package com.expensetracker.gui.controller;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.pojo.UserUpdateRequest;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.manager.JobManager;
import com.expensetracker.manager.UserManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ResetPasswordController {
	
	@FXML
	private Label usernameLabel;

	@FXML
	private PasswordField oldPasswordField, newPasswordField, confirmPasswordField;
	
	@FXML
	private Button resetPasswordBtn;

	/**
	 * @return the oldPasswordField
	 */
	public PasswordField getOldPasswordField() {
		return oldPasswordField;
	}

	/**
	 * @param oldPasswordField the oldPasswordField to set
	 */
	public void setOldPasswordField(PasswordField oldPasswordField) {
		this.oldPasswordField = oldPasswordField;
	}

	/**
	 * @return the newPasswordField
	 */
	public PasswordField getNewPasswordField() {
		return newPasswordField;
	}

	/**
	 * @param newPasswordField the newPasswordField to set
	 */
	public void setNewPasswordField(PasswordField newPasswordField) {
		this.newPasswordField = newPasswordField;
	}

	/**
	 * @return the confirmPasswordField
	 */
	public PasswordField getConfirmPasswordField() {
		return confirmPasswordField;
	}

	/**
	 * @param confirmPasswordField the confirmPasswordField to set
	 */
	public void setConfirmPasswordField(PasswordField confirmPasswordField) {
		this.confirmPasswordField = confirmPasswordField;
	}

	/**
	 * @return the resetPasswordBtn
	 */
	public Button getResetPasswordBtn() {
		return resetPasswordBtn;
	}

	/**
	 * @param resetPasswordBtn the resetPasswordBtn to set
	 */
	public void setResetPasswordBtn(Button resetPasswordBtn) {
		this.resetPasswordBtn = resetPasswordBtn;
	}
	
	/**
	 * @return the usernameLabel
	 */
	public Label getUsernameLabel() {
		return usernameLabel;
	}

	/**
	 * @param usernameLabel the usernameLabel to set
	 */
	public void setUsernameLabel(Label usernameLabel) {
		this.usernameLabel = usernameLabel;
	}

	@FXML
	public void resetPassword(ActionEvent event) {
		String errorMsg = "";
		String username = usernameLabel.getText();
		String oldpass = oldPasswordField.getText();
		String newpass = newPasswordField.getText();
		String confirmpass = confirmPasswordField.getText();
		
		User user = UserManager.getUserManager().getUser(username);
		
		if (!user.getPassword().equals(oldpass)) {
			errorMsg += "Old Passwrd is wrong, Please enter right password!\n";
		} else {
			if ((newpass.length() < 6 ) || (!newpass.equals(confirmpass))) {
				errorMsg += "New password mismatch!";
			}
		}
		
		if (errorMsg.length() == 0) {
			UserUpdateRequest obj = new UserUpdateRequest(username, user.getStatus(), newpass);
			Job job = new Job(Constants.RESET_PROFILE_PASSWORD, obj);
			int ret = JobManager.getInstantJobHandler().processJob(job);
			if (ret == Constants.JOB_PROCESSED_SUCCESSFULLY) {
				PopupWindow.popup.createInformationalPopup("Success!", "Password reseted successfully");
			}
		} else {
			PopupWindow.popup.createErrorPopup("Error!", errorMsg);
		}
		
		clear();
	}

	private void clear() {
		// TODO Auto-generated method stub
		oldPasswordField.clear();
		newPasswordField.clear();
		confirmPasswordField.clear();
	}
}
