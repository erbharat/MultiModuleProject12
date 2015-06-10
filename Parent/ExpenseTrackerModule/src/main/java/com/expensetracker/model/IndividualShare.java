package com.expensetracker.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
@Entity
@Table(name="individualshare")
public class IndividualShare {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SHAREID",nullable=false)
	private int shareId;
	@Column(name="USERNAME",nullable=false)
	private String userName;
	@Column(name="AMOUNT")
	private double amount;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	@Column(name="DATE", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date date;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PURCHASEID")
	private Purchase purchaseId;
	
	public int getShareId() {
		return shareId;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Purchase getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Purchase purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	@Override
	public String toString() {
		return "IndividualShare [shareId=" + shareId + ", userName=" + userName
				+ ", amount=" + amount + ", date=" + date + ", purchaseId="
				+ purchaseId + "]";
	}
}
