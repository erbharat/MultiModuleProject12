package com.expensetracker.exception;

public class ExpencesTrackerRunTimeException extends Exception {

	
	private static final long serialVersionUID = 1L;
	private int errorMessage ;
	private Exception cause;
	public final static int DATABASE_CONNECTION_SUCCESS = 1000;
	public final static int DATABASE_CONNECTION_FAIL = 1001;
	public final static int USER_CREDENTIAL_IS_VALID =100;
	public final static int USER_CREDENTIAL_NOT_VALID = 101;
	
	public ExpencesTrackerRunTimeException(int errorMessage ,Exception cause ) {
		this.errorMessage = errorMessage;
		this.cause = cause;
	}
	public int authenticationException(){
		return errorMessage;
	}
	
	public int getConnectionException(){
		return errorMessage;
	}
	
	
}
