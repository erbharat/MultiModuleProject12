package com.expensetracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PURCHASE")
public class Purchase {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID",nullable=false, unique=true,length=15)
	private int purchaseId;
	
	@Column(name="PURCHASETYPE", length=20, nullable=false)
	private String purchaseType;
	
	@Column(name="PURCHASEDATE", nullable=false)
	private String purchaseDate;
	
	@Column(name="USERID", length=15, nullable=false)
	private String userId;
	
	@Column(name="ITEMLIST", nullable=true)
	private String itemList;
	
	@Column(name="CONTRIBUTORLIST", nullable=true)
	private String contributorList;
	
	@Column(name="CONTRIBUTIONLIST",nullable=true)
	private String contributionList;
	
	@Column(name="TOTALAMOUNT",  nullable=false)
	private double totalAmount;
	
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getItemList() {
		return itemList;
	}
	public void setItemList(String itemList) {
		this.itemList = itemList;
	}
	public String getContributorList() {
		return contributorList;
	}
	public void setContributorList(String contributorList) {
		this.contributorList = contributorList;
	}
	public String getContributionList() {
		return contributionList;
	}
	public void setContributionList(String contributionList) {
		this.contributionList = contributionList;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", purchaseType="
				+ purchaseType + ", purchaseDate=" + purchaseDate + ", userId="
				+ userId + ", itemList=" + itemList + ", contributorList="
				+ contributorList + ", contributionList=" + contributionList
				+ ", totalAmount=" + totalAmount + "]";
	}	
	
	
}
