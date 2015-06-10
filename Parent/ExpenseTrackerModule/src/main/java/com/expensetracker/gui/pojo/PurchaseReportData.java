package com.expensetracker.gui.pojo;

import java.util.Date;
import java.util.List;

public class PurchaseReportData {
	private int purchaseId;
	private String userName;
	private String purchaseType;
	private List<String> itemList;
	private List<String> contributorList;
	private List<String> contributionList;
	private Date date;
	private double totalAmount;

	public PurchaseReportData() {
	}
	public PurchaseReportData(int purchaseId, String userName,
			String purchaseType, List<String> itemList, List<String> contributorList,
			List<String> contributionList, Date date, double totalAmount) {
		super();
		this.purchaseId = purchaseId;
		this.userName = userName;
		this.purchaseType = purchaseType;
		this.itemList = itemList;
		this.contributorList = contributorList;
		this.contributionList = contributionList;
		this.date = date;
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}



	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
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
	 * @return the purchaseType
	 */
	public String getPurchaseType() {
		return purchaseType;
	}

	/**
	 * @param purchaseType
	 *            the purchaseType to set
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * @return the itemList
	 */
	public List<String> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList
	 *            the itemList to set
	 */
	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return the contributorList
	 */
	public List<String> getContributorList() {
		return contributorList;
	}

	/**
	 * @param contributorList
	 *            the contributorList to set
	 */
	public void setContributorList(List<String> contributorList) {
		this.contributorList = contributorList;
	}

	/**
	 * @return the contributionList
	 */
	public List<String> getContributionList() {
		return contributionList;
	}

	/**
	 * @param contributionList
	 *            the contributionList to set
	 */
	public void setContributionList(List<String> contributionList) {
		this.contributionList = contributionList;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the purchaseId
	 */
	public int getPurchaseId() {
		return purchaseId;
	}

	/**
	 * @param purchaseId
	 *            the purchaseId to set
	 */
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PurchaseReportData [userName=" + userName + ", purchaseType="
				+ purchaseType + ", itemList=" + itemList
				+ ", contributorList=" + contributorList
				+ ", contributionList=" + contributionList + ", date=" + date
				+ ", purchaseId=" + purchaseId + "]";
	}

}
