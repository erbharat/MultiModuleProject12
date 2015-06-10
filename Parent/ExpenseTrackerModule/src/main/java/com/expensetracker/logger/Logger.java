package com.expensetracker.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.expensetracker.constants.Constants;

public class Logger {

	private boolean debug = false;

	private static File log = new File(Constants.LOG_FILE_PATH
			+ Constants.LOG_FILE_NAME);
	private static RandomAccessFile writer = null;
	private static SimpleDateFormat dateFormate = new SimpleDateFormat(
			"dd-mm-yyyy hh:mm:ss");
	private static Date date = null;

	private static Timer timer;

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public static void initializeLogger() {
		try {
			writer = new RandomAccessFile(log, "rw");
			if (log.exists()) {
				writer.seek(log.length());
			}
			timer = new Timer();
			timer.schedule(new RotateLogs(), new Date(),
					Constants.JOB_TIMER_INTERVAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void reInitializeLogs(File log) {
		try {
			writer = new RandomAccessFile(log, "rw");
			writer.setLength(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeLogger() {

		try {
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer = null;

	}

	public static void logMessage(String message) {

		synchronized (Logger.class) {

			if (true && writer != null) { // TODO NEED TO REPLACE WITH DEBUG
											// VARIABLE.
				date = new Date();
				try {
					writer.writeBytes(dateFormate.format(date) + " : "
							+ message);
					writer.writeBytes("\r");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static class RotateLogs extends TimerTask {

		@Override
		public void run() {
			long size = log.length();
			if (size >= Constants.LOG_FILE_SIZE_LIMIT) {
				rotate();
			}
		}

		private void rotate() {

			synchronized (Logger.class) {
				File parent = log.getParentFile();
				int count = ((File[]) parent.listFiles()).length;
				// closeLogger();
				File newFile = new File(Constants.LOG_FILE_PATH
						+ Constants.LOG_FILE_NAME + count);
				Path source = Paths.get(log.getAbsolutePath());
				Path dest = Paths.get(newFile.getAbsolutePath());
				try {
					if (!newFile.exists()) {
						Files.copy(source, dest);
						writer.setLength(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
