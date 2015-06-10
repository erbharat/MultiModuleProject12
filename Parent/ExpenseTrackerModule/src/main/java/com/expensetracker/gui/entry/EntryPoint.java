package com.expensetracker.gui.entry;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import com.expensetracker.config.ResourceConfig;
import com.expensetracker.gui.controller.ScreenController;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.JobManager;
import com.expensetracker.manager.ReportManager;
import com.expensetracker.manager.UserManager;

public class EntryPoint extends Application {

	private ScreenController screenController = null;
	private static String appName = "Expense Tracker";
	private static File file;
	private static FileChannel channel;
	private static FileLock lock;

	@Override
	public void start(Stage primaryStage) {
		screenController = new ScreenController();
		screenController.setPrimaryStage(primaryStage);
		screenController.showMainScreen();
	}

	public static void main(String[] args) {
		System.out.println(ResourceConfig.getInstance().getDBConnectionURL());
		if (isAppActive()) {
			generateAlreadyRunningDialog();
			System.exit(1);
		} else {
			initialize();
			launch(args);
		}
	}

	private static void generateAlreadyRunningDialog() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Expense Tracker is already running.\nPlease check it in system tray available on right side bottom.");
	}

	public static boolean isAppActive() {
		try {
			file = new File(System.getProperty("user.home"), appName + ".tmp");
			channel = new RandomAccessFile(file, "rw").getChannel();

			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				// already locked
				closeLock();
				return true;
			}

			if (lock == null) {
				closeLock();
				return true;
			}

			Runtime.getRuntime().addShutdownHook(new Thread() {
				// destroy the lock when the JVM is closing
				public void run() {
					closeLock();
					deleteFile();
				}
			});
			return false;
		} catch (Exception e) {
			closeLock();
			return true;
		}
	}

	private static void closeLock() {
		try {
			lock.release();
		} catch (Exception e) {
		}
		try {
			channel.close();
		} catch (Exception e) {
		}
	}

	private static void deleteFile() {
		try {
			file.delete();
		} catch (Exception e) {
		}
	}

	private static void initialize() {
		// TODO Auto-generated method stub
		Logger.initializeLogger();
		Logger.logMessage("EntryPoint.initialize : Expence Tracker Application Initiated..");
		UserManager.getUserManager().populateUserListFromDatabase();
		JobManager.getTimedwaitJobHandler().createAndStartTimer();
		JobManager.getJobManager().readObjectFromDisk();
		ReportManager.getSchdldReportGenerator().createAndStartTimer();

	}
}
