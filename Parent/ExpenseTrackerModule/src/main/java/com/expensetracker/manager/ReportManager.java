package com.expensetracker.manager;

import java.io.File;

import com.expensetracker.gui.pojo.Job;
import com.expensetracker.handler.InstantReportGenerator;
import com.expensetracker.handler.ScheduledReportGenerator;

public class ReportManager {
	private final static ReportManager reportManager = new ReportManager();
	private final static InstantReportGenerator instantReportGenerator = InstantReportGenerator
			.getInstantReportHandler();
	private final static ScheduledReportGenerator schdldReportGenerator = ScheduledReportGenerator
			.getScheduledReportGenerator();

	public File generateReportInstant(Job job) {
		return InstantReportGenerator.getInstantReportHandler().generateReportNow(job);
	}

	/**
	 * @return the reportManager
	 */
	public static ReportManager getReportManager() {
		return reportManager;
	}

	/**
	 * @return the instantReportGenerator
	 */
	public static InstantReportGenerator getInstantReportGenerator() {
		return instantReportGenerator;
	}

	/**
	 * @return the schdldReportGenerator
	 */
	public static ScheduledReportGenerator getSchdldReportGenerator() {
		return schdldReportGenerator;
	}
}
