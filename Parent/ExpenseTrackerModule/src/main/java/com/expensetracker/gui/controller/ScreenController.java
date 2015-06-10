package com.expensetracker.gui.controller;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;

import com.expensetracker.gui.screen.DashboardScreen;
import com.expensetracker.gui.screen.EditProfileScreen;
import com.expensetracker.gui.screen.LoginScreen;
import com.expensetracker.gui.screen.ProfileScreen;
import com.expensetracker.gui.screen.ResetPasswordScreen;
import com.expensetracker.gui.screen.ScreenConstant;
import com.expensetracker.gui.screen.ViewProfileScreen;
import com.expensetracker.gui.screen.WelcomeScreen;
import com.expensetracker.gui.util.ControllerUtil;
import com.expensetracker.gui.util.ImageUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.JobManager;

public class ScreenController {

	public static Stage stage;
	private BorderPane rootLayout;
	private VBox rootLayout1;

	private ControllerUtil controllerUtil = ControllerUtil.controllerUtil;
	private boolean firstTime;
	private TrayIcon trayIcon;

	private void intializeScreens() {
		Logger.logMessage("Initializing Screens...");
		WelcomeScreen.initializeWelcomeScreen(this);
		ProfileScreen.initializeProfileScreen(this);
		LoginScreen.initializeLoginScreen(this);
		DashboardScreen.initializeDashboardScreen(this);
		ViewProfileScreen.initializeViewProfileScreen(this);
		EditProfileScreen.initializeEditProfileScreen(this);
		ResetPasswordScreen.initializeResetPasswordScreen(this);
		createTrayIcon(stage);
		firstTime = true;
		Platform.setImplicitExit(false);
	}

	public void setPrimaryStage(Stage primaryStage) {
		try {
			Logger.logMessage("ScreenController.setPrimaryStage(.) : Setting primary stage.. ");
			stage = primaryStage;
			stage.getIcons().add(new Image(ImageUtil.getAppIconPath()));
			stage.setTitle("ExpenseTracker");
			intializeScreens();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public FXMLLoader showMainScreen() {
		FXMLLoader loader = null;
		try {
			// Load the root layout from the fxml file
			// loader = new FXMLLoader(WelcomeScreen.getWelcomeScreen()
			// .getFXMLResource());
			// rootLayout = (BorderPane) loader.load();
			Logger.logMessage("ScreenController.showMainScreen() : Loading main screen " + ScreenConstant.WELCOME_SCREEN);
			loader = new FXMLLoader(WelcomeScreen.getWelcomeScreen().getFXMLResource());
			rootLayout1 = (VBox) loader.load();
			Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			double width = visualBounds.getMaxX();
			double height = visualBounds.getMaxY();
			Scene scene = new Scene(rootLayout1);
			stage.setScene(scene);
			setController(loader, ScreenConstant.WELCOME_SCREEN);
			loadScreen(ScreenConstant.LOGIN_SCREEN, LoginScreen
					.getLoginScreen().getFXMLResource());
			stage.setResizable(false);
			stage.show();
			Logger.logMessage("ScreenController.showMainScreen() : Loaded main screen");
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
			Logger.logMessage("ScreenController.showMainScreen() : Error Loading main screen, errortace --> " + e.getLocalizedMessage());
		}

		return loader;
	}

	public boolean loadScreen(String screenName, URL url) {
		boolean isLoaded = false;
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader();
			loader.setLocation(url);
			StackPane layout = (StackPane) loader.load();

			WelcomeScreenController screenSwitchController = (WelcomeScreenController) controllerUtil
					.getController(ScreenConstant.WELCOME_SCREEN);
			screenSwitchController.screenSwitcher.getChildren().setAll(layout);
			setController(loader, screenName);
			isLoaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isLoaded;
	}

	private void setController(FXMLLoader loader, String screenName) {
		switch (screenName) {
		case ScreenConstant.WELCOME_SCREEN:
			controllerUtil.setController(ScreenConstant.WELCOME_SCREEN,
					loader.<WelcomeScreenController> getController());
			break;
		case ScreenConstant.LOGIN_SCREEN:
			controllerUtil.setController(ScreenConstant.LOGIN_SCREEN,
					loader.<LoginScreenController> getController());
			break;
		case ScreenConstant.PROFILE_SCREEN:
			controllerUtil.setController(ScreenConstant.PROFILE_SCREEN,
					loader.<ProfileScreenController> getController());
			break;
		case ScreenConstant.DASHBOARD_SCREEN:
			controllerUtil.setController(ScreenConstant.DASHBOARD_SCREEN,
					loader.<DashboardScreenController> getController());
			break;
		default:
			break;
		}
	}

	public void createTrayIcon(final Stage stage) {
		if (SystemTray.isSupported()) {
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			java.awt.Image image = null;
			try {
				URL url = ImageUtil.getTrayIconURL();
				image = ImageIO.read(url);
			} catch (IOException ex) {
				Logger.logMessage(""+ ex);
			}

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					hide(stage);
				}
			});
			// create a action listener to listen for default action executed on
			// the tray icon
			final ActionListener closeListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Logger.logMessage("Closing application... Saving all data");
					JobManager.getJobManager().saveObjectToDisk();
					System.exit(0);
				}
			};

			ActionListener showListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.show();
						}
					});
				}
			};

			// create a popup menu
			PopupMenu popup = new PopupMenu();
			MenuItem showItem = new MenuItem("Show");
			showItem.addActionListener(showListener);
			popup.add(showItem);

			MenuItem closeItem = new MenuItem("Close");
			closeItem.addActionListener(closeListener);
			popup.add(closeItem);

			// construct a TrayIcon
			trayIcon = new TrayIcon(image, "ExpenseTracker", popup);
			trayIcon.addActionListener(showListener);
			// ...
			// add the tray image
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
			// ...
		}
	}

	public void showProgramIsMinimizedMsg() {
		if (firstTime) {
			trayIcon.displayMessage("Expense Tracker is hidden.",
					"To re-open right click and click show.",
					TrayIcon.MessageType.INFO);
			firstTime = false;
		}
	}

	private void hide(final Stage stage) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (SystemTray.isSupported()) {
					stage.hide();
					showProgramIsMinimizedMsg();
				} else {
					System.exit(0);
				}
			}
		});
	}
}
