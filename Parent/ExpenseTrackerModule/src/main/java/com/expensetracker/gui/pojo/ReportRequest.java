package com.expensetracker.gui.pojo;

import java.io.Serializable;
import java.util.Date;

public class ReportRequest implements Serializable{

	private String reportType;
	private String month;
	private String year;
	private String fromDate;
	private String toDate;
	private String fileOption;
	
	public ReportRequest(String reportType, String month, String year,
			String fromDate, String toDate, String fileOption) {
		super();
		this.reportType = reportType;
		this.month = month;
		this.year = year;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fileOption = fileOption;
	}

	/**
	 * @return the fileOption
	 */
	public String getFileOption() {
		return fileOption;
	}

	/**
	 * @param fileOption the fileOption to set
	 */
	public void setFileOption(String fileOption) {
		this.fileOption = fileOption;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportRequest [reportType=" + reportType + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}
}
