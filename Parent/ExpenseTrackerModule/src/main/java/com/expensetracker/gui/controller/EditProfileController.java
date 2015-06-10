package com.expensetracker.gui.controller;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.util.ControllerUtil;
import com.expensetracker.gui.util.ImageUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.JobManager;
import com.expensetracker.manager.UserManager;

public class EditProfileController implements Initializable {

	@FXML
	private TextField usernameTxt, firstNameText, lastnameTxt, contactTxt,
			emailIdTxt;

	/**
	 * @return the usernameTxt
	 */
	public void disableUsernameTextfield() {
		usernameTxt.setEditable(false);
	}



	@FXML
	private Button saveProfileBtn, changeProfilePicBtn;

	@FXML
	private StackPane userImage;

	public void initialize(URL location, ResourceBundle resources) {
		this.userImage.getChildren().add(
				new ImageView(new Image(ImageUtil.getDefaultUserPath())));
	}

	/**
	 * @param usernameTxt
	 *            the usernameTxt to set
	 */
	public void setUsernameTxt(String usernameTxt) {
		this.usernameTxt.setText(usernameTxt);
	}

	/**
	 * @param firstNameText
	 *            the firstNameText to set
	 */
	public void setFirstNameText(String firstNameText) {
		this.firstNameText.setText(firstNameText);
	}

	/**
	 * @param lastnameTxt
	 *            the lastnameTxt to set
	 */
	public void setLastnameTxt(String lastnameTxt) {
		this.lastnameTxt.setText(lastnameTxt);
	}

	/**
	 * @param contactTxt
	 *            the contactTxt to set
	 */
	public void setContactTxt(String contactTxt) {
		this.contactTxt.setText(contactTxt);
		;
	}

	/**
	 * @param emailIdTxt
	 *            the emailIdTxt to set
	 */
	public void setEmailIdTxt(String emailIdTxt) {
		this.emailIdTxt.setText(emailIdTxt);
		;
	}

	/**
	 * @param userImageView
	 *            the userImageView to set
	 */
	public void setUserImageView(Image userImage) {
		this.userImage.getChildren().add(new ImageView(userImage));
	}

	@FXML
	public void onButtonAction(ActionEvent e) {
		Button source = (Button) e.getSource();
		File file = null;
		Job job = null;
		Image image = null;
		if (source == changeProfilePicBtn) {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("All Images", "*.*"),
					new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"));

			file = fileChooser.showOpenDialog(ScreenController.stage);
			if (file == null) {
				Logger.logMessage("EditProfileController.onButtonAction(): User cancelled file selection " + file);
				return;
			}
			BufferedImage bufferedImage = null;
			try {
				bufferedImage = ImageUtil.getScaledImage(ImageIO.read(file), 150, 120);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            //setUserImageView(image);
            job = createJob(file);

		} else {
			job = createJob(file);
		}
		
		// 1- Add to job handler.
		int ret = JobManager.getInstantJobHandler().processJob(job);
		if (ret == Constants.JOB_PROCESSED_SUCCESSFULLY) {
			setUserImageView(image);
			PopupWindow.popup.createInformationalPopup("Success!",
					"Profile got updated successfully");

		}
	}
	
   

	private Job createJob(File file) {
		User user = UserManager.getUserManager().getUser(usernameTxt.getText());
		// Validation required.
		user.setFirstname(firstNameText.getText());
		user.setLastName(lastnameTxt.getText());
		user.setEmailId(emailIdTxt.getText());
		user.setMobileNumber(contactTxt.getText());
		if (file != null) {
/*			byte[] b = new byte[(int) file.length()];
					
					try {
					    FileInputStream fileInputStream = new FileInputStream(file);
					    fileInputStream.read(imageData);
					    fileInputStream.close();
					} catch (Exception e) {
					    e.printStackTrace();
					}*/
			BufferedImage bufferedImage = null;
			try {
				bufferedImage = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] b = null;
			String[] extension = file.getName().split("\\.");
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(262144)) {
                ImageIO.write(bufferedImage, extension[extension.length - 1], out);
                out.flush();
                b = out.toByteArray();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            user.setImage(b);
		}
		
		Job job = new Job(Constants.UPDATE_PROFILE, user);
		return job;
	}
}
