package com.expensetracker.util;

public class Constants {
	/*******************TIMER CONSTANT*******************/
	public final static long JOB_TIMER_INTERVAL = 60*1000;
	public final static long REPORT_TIMER_INTERVAL = 5*60*1000;

	/******************JOB/SERVICE CONSTANT*******************/
	public final static String NEW_PROFILE = "NEW_PROFILE";
	public final static String COMMON_PURCHASE_DETAIL = "COMMON_PURCHASE_DETAIL";
	public final static String INSTANT_REPORT = "INSTANT_REPORT";
	public static final String PERSIST_DIRECTORY_PATH = "C:\\ProgramData\\ExpenseTracker";
	public static final String PERSIST_FILE_NAME = "expensetrackerdata.ser";
	
	/**********************EMAIL CONSTANTS********************/
	public final static String REPORT_ADMIN_ID = "expensetrackerreport@gamil.com";
	public final static String REPORT_ADMIN_FROM = "expensetrackerreport@gamil.com";
	public final static String REPORT_ADMIN_PWD = "donotreply!";
	public final static String MAIL_SMTP_AUTH = "true";
	public final static String MAIL_SMTP_STARTTLS_ENABLE = "true";
	public final static String MAIL_SMTP_HOST = "smtp.gmail.com";
	public final static String MAIL_SMTP_PORT = "587";
	
	/**********************ERROR CONSTANTS********************/
	public final static int DATABASE_CONNECTION_SUCCESS = 1000;
	public final static int DATABASE_CONNECTION_ERROR = 1001;
	public static final int SUCCESS = 0;

}
