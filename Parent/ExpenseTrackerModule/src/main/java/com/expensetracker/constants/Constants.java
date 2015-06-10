package com.expensetracker.constants;

public class Constants {

	/******************* TIMER CONSTANT *******************/
	public final static long JOB_TIMER_INTERVAL = 10 * 60 * 1000;
	public final static long REPORT_TIMER_INTERVAL = 5 * 60 * 1000;

	/****************** JOB/SERVICE CONSTANT *******************/
	public final static String NEW_PROFILE = "NEW_PROFILE";
	public static final String REMOVE_USER = "REMOVE_USER";
	public final static String COMMON_PURCHASE_DETAIL = "COMMON_PURCHASE_DETAIL";
	public static final String UPDATE_PROFILE ="UPDATE_PROFILE";
	public static final String RESET_PROFILE_PASSWORD = "RESET_PROFILE_PASSWORD";
	public static final String ACTIVATE_USER = "ACTIVATE_USER";
	public static final String DEACTIVATE_USER = "DEACTIVATE_USER";
	public static final String PERSIST_DIRECTORY_PATH = "C:\\ProgramData\\ExpenseTracker";
	public static final String PERSIST_FILE_NAME = "expensetrackerdata.ser";
	public static final int JOB_PROCESSED_SUCCESSFULLY = 100;
	public static final int JOB_FAILED_DUE_TO_DB_CONN_ERROR = 101;
	public static final int JOB_FAILED_DUE_TO_EXCEPTION = 102;
	public static final int JOB_IN_WAITING_LIST = 103;
	public static final int JOB_REPORT_GENERATION_ERR = 104;

	/******************* REPORT CONSTANT *******************/
	public final static String REPORT_FILE_PATH = "report\\";
	public final static String INSTANT_REPORT_DOWNLOAD = "INSTANT_REPORT_DOWNLOAD";
	public final static String INSTANT_REPORT_EMAIL = "INSTANT_REPORT_EMAIL";
	public final static String PURCHASE_REPORT = "PURCHASE REPORT";
	public final static String SHARE_REPORT = "SHARE REPORT";
	public final static String PDF_FILE_REPORT = "PDF";
	public final static String DOCX_FILE_REPORT = "DOCX";

	/********************** EMAIL CONSTANTS ********************/
	public final static String REPORT_ADMIN_ID = "expensetrackerreport@gmail.com";
	public final static String REPORT_ADMIN_FROM = "expensetrackerreport@gmail.com";
	public final static String REPORT_ADMIN_PWD = "donotreply!";
	public final static String MAIL_SMTP_AUTH = "true";
	public final static String MAIL_SMTP_STARTTLS_ENABLE = "true";
	public final static String MAIL_SMTP_HOST = "smtp.gmail.com";
	public final static String MAIL_SMTP_PORT = "587";

	/********************** ERROR CONSTANTS ********************/
	public final static int DATABASE_CONNECTION_SUCCESS = 1000;
	public final static int DATABASE_CONNECTION_ERROR = 1001;
	public static final int SUCCESS = 0;
	public static final int ERROR = -1;

	/***************** PURCHASE/USER CONSTANTS ****************/
	public static final String BULK_PURCHASE_TYPE = "BULK PURCHASE";
	public static final String DAILY_PURCHASE_TYPE = "DAILY PURCHASE";
	public static final String HOUSE_RENT_TYPE = "HOUSE RENT";

	/******************** LOGGER CONSTANTS ********************/
	public static final String LOG_FILE_PATH = "\\";
	public static final String LOG_FILE_NAME = "ExpenceTrackerLog";
	public static final int LOG_FILE_SIZE_LIMIT = 10 * 1024 * 1024;

	/********************** DAO CONSTANTS *********************/
	public static final String USER_DAO = "USER_DAO";
	public static final String PURCHASE_DAO = "PURCHASE_DAO";
	public static final String INDIVIDUAL_SHARE_DAO = "INDIVIDUAL_SHARE_DAO";
	public static final String AGGREGATE_SHARE_DAO = "AGGREGATE_SHARE_DAO";
	
	/**********************USERS OPERATIONS********************/
	public static final String VIEW_PROFILE = "VIEW PROFILE";
	public static final String EDIT_PROFILE = "EDIT PROFILE";
	public static final String SET_USER_ACTIVE = "MAKE USER ACTIVE";
	public static final String SET_USER_INACTIVE = "MAKE USER INACTIVE";
	public static final String DELETE_USER = "DELETE USER";
	public static final String RESET_PASSWORD = "RESET PASSWORD";
	
	/******************* DB Properties *******************/
	public static final String DRIVER_NAME_PROPERTY_KEY = "hibernate.connection.driver_class";
	public static final String URL_PROPERTY_KEY = "hibernate.connection.url";
	public static final String USERNAME_PROPERTY_KEY = "hibernate.connection.username";
	public static final String PASSWORD_PROPERTY_KEY = "hibernate.connection.password";
}
