package com.expensetracker.gui.pojo;

public class UserUpdateRequest {

	private String username;
	private byte status;
	private String newPassword;
	/**
	 * @return the username
	 */
	
	public UserUpdateRequest(String username, byte status,String newPassword) {
		this.username = username;
		this.status = status;
		this.setNewPassword(newPassword);
	}
	
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
