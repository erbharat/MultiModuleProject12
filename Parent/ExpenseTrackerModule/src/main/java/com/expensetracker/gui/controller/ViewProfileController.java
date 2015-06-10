package com.expensetracker.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.expensetracker.gui.util.ImageUtil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ViewProfileController implements Initializable{
	@FXML
	private Label usernameLabel, firstnameLabel, lastnameLabel, contactLabel, emailIdLabel;
	
	@FXML 
	private StackPane userImage;

	/**
	 * @param usernameLabel the usernameLabel to set
	 */
	public void setUsernameLabel(String usernameLabel) {
		this.usernameLabel.setText(usernameLabel);
	}

	/**
	 * @param firstnameLabel the firstnameLabel to set
	 */
	public void setFirstnameLabel(String firstnameLabel) {
		this.firstnameLabel.setText(firstnameLabel);
	}

	/**
	 * @param lastnameLabel the lastnameLabel to set
	 */
	public void setLastnameLabel(String lastnameLabel) {
		this.lastnameLabel.setText(lastnameLabel);
	}

	/**
	 * @param contactLabel the contactLabel to set
	 */
	public void setContactLabel(String contactLabel) {
		this.contactLabel.setText(contactLabel);
	}

	/**
	 * @param emailIdLabel the emailIdLabel to set
	 */
	public void setEmailIdLabel(String emailIdLabel) {
		this.emailIdLabel.setText(emailIdLabel);
	}

	/**
	 * @param userImageView the userImageView to set
	 */
	public void setUserImageView(Image userImage) {
		this.userImage.getChildren().add(new ImageView(userImage));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	/*	System.out.println("Intializing view profile " + ImageUtil.getDefaultUserPath());
		userImageView.setImage(new Image(ImageUtil.getDefaultUserPath()));*/
		this.userImage.getChildren().add(new ImageView(new Image(ImageUtil.getDefaultUserPath())));
	}
}
