package com.expensetracker.gui.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchase implements Serializable{

	private static final long serialVersionUID = 8731827317283142722L;
	
	private String purchaseType;
	private String date;
	private String member;
	private List<String> contributorList = new ArrayList<String>();
	private List<String> contributionList = new ArrayList<String>();
	private String itemsList;
	private String totalAmount;
	
	/**
	 * @return the purchaseType
	 */
	public String getPurchaseType() {
		return purchaseType;
	}
	/**
	 * @param purchaseType the purchaseType to set
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the member
	 */
	public String getMember() {
		return member;
	}
	/**
	 * @param member the member to set
	 */
	public void setMember(String member) {
		this.member = member;
	}
	/**
	 * @return the contributorList
	 */
	public List<String> getContributorList() {
		return contributorList;
	}
	/**
	 * @param contributorList the contributorList to set
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
	 * @param contributionList the contributionList to set
	 */
	public void setContributionList(List<String> contributionList) {
		this.contributionList = contributionList;
	}
	/**
	 * @return the itemsList
	 */
	public String getItemsList() {
		return itemsList;
	}
	/**
	 * @param itemsList the itemsList to set
	 */
	public void setItemsList(String itemsList) {
		this.itemsList = itemsList;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PurchaseDetail [purchaseType=" + purchaseType + ", date="
				+ date + ", member=" + member + ", contributorList="
				+ contributorList + ", contributionList=" + contributionList
				+ ", itemsList=" + itemsList + ", totalAmount=" + totalAmount
				+ "]";
	}
}
