package com.expensetracker.gui.pojo;

import java.util.ArrayList;
import java.util.List;

public class ShareReportData {
	private String userName;
	private double amount;
	private List<String> memberToList;
	private List<String> amountToList;
	private List<String> memberFromList;
	private List<String> amountFromList;

	public ShareReportData(){
		memberToList = new ArrayList<String>();
		amountToList = new ArrayList<String>();
		memberFromList = new ArrayList<String>();
		amountFromList = new ArrayList<String>();
	}
	
	public ShareReportData(String userName, double amount, List<String> memberToList,
			List<String> amountToList, List<String> memberFromList, List<String> amountFromList) {
		super();
		this.userName = userName;
		this.amount = amount;
		this.memberToList = memberToList;
		this.amountToList = amountToList;
		this.memberFromList = memberFromList;
		this.amountFromList = amountFromList;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the memberToList
	 */
	public List<String> getMemberToList() {
		return memberToList;
	}

	/**
	 * @param memberToList
	 *            the memberToList to set
	 */
	public void setMemberToList(List<String> memberToList) {
		this.memberToList = memberToList;
	}

	/**
	 * @return the amountToList
	 */
	public List<String> getAmountToList() {
		return amountToList;
	}

	/**
	 * @param amountToList
	 *            the amountToList to set
	 */
	public void setAmountToList(List<String> amountToList) {
		this.amountToList = amountToList;
	}

	/**
	 * @return the memberFromList
	 */
	public List<String> getMemberFromList() {
		return memberFromList;
	}

	/**
	 * @param memberFromList
	 *            the memberFromList to set
	 */
	public void setMemberFromList(List<String> memberFromList) {
		this.memberFromList = memberFromList;
	}

	/**
	 * @return the amountFromList
	 */
	public List<String> getAmountFromList() {
		return amountFromList;
	}

	/**
	 * @param amountFromList
	 *            the amountFromList to set
	 */
	public void setAmountFromList(List<String> amountFromList) {
		this.amountFromList = amountFromList;
	}

}
