package com.expensetracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aggregateshare")
public class AggregateShare {
	

	@Id
	@Column(name="USERNAME")
	private String userName;
	
	@Column(name="CURRENTSHARE")
	private double currentShare;
	
	public AggregateShare(String userName,int currentShare) {
		this.currentShare=currentShare;
		this.userName=userName;
	}
	
	public AggregateShare() {
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getCurrentShare() {
		return currentShare;
	}
	public void setCurrentShare(double currentShare) {
		this.currentShare = currentShare;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AggregateShare [userName=" + userName + ", currentShare="
				+ currentShare + "]";
	}
}
