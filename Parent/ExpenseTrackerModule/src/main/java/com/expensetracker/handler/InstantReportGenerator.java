package com.expensetracker.handler;

import java.io.File;
import java.util.List;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.PurchaseReportData;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.pojo.ShareReportData;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.util.DatabaseUtil;
import com.expensetracker.gui.util.ReportUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.JobManager;
import com.expensetracker.model.AggregateShare;
import com.expensetracker.model.Purchase;
import com.expensetracker.service.Service;

public class InstantReportGenerator {
	private final static InstantReportGenerator instantReportHandler = new InstantReportGenerator();

	/**
	 * @return the instantReportHandler
	 */
	public static InstantReportGenerator getInstantReportHandler() {
		return instantReportHandler;
	}

	public File generateReportNow(Job job) {
		File file = null;
		try {
			int errorCode = DatabaseUtil.checkDatabaseConnection();
			Logger.logMessage("InstantReportGenerator.generateReportNow() : is database connectin alive ? "
					+ errorCode);
			if (errorCode == Constants.DATABASE_CONNECTION_SUCCESS) {

				switch (((ReportRequest) job.getJob()).getReportType()) {
				case Constants.PURCHASE_REPORT:

					List<Purchase> purchase = null;
					try {
						purchase = Service.getService().getPurchaseReport(job);
					} catch (Exception e) {
						errorCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
						Logger.logMessage("InstantReportGenerator.generateReportNow() : "
								+ job + " " + e.getMessage());
					}
					List<PurchaseReportData> purchaseList = null;
					try {
						purchaseList = DatabaseUtil
								.convertPurchaseModelToReportData(purchase);
						file = ReportUtil.generatePurchaseReport(purchaseList,
								job);
					} catch (Exception exc) {
						errorCode = Constants.JOB_REPORT_GENERATION_ERR;
						exc.printStackTrace();
						Logger.logMessage("InstantReportGenerator.generateReportNow() : Error trace "
								+ exc);
					}

					Logger.logMessage("InstantReportGenerator.generateReportNow() : file generated  "
							+ file
							+ " converted pojo purchase list "
							+ purchaseList);
					break;
				case Constants.SHARE_REPORT:

					List<AggregateShare> share = null;
					try {
						share = Service.getService().getShareReport(job);
					} catch (Exception e) {
						errorCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
						Logger.logMessage("InstantReportGenerator.generateReportNow() : "
								+ job + " " + e.getMessage());
					}

					Logger.logMessage("InstantReportGenerator.generateReportNow() : share list returned from database "
							+ share);
					List<ShareReportData> shareList = null;
					try {
						shareList = DatabaseUtil
								.convertAggregateShareModelToShareData(share);
						file = ReportUtil.generateShareReport(shareList, job);

					} catch (Exception exc) {
						errorCode = Constants.JOB_REPORT_GENERATION_ERR;
						exc.printStackTrace();
						Logger.logMessage("InstantReportGenerator.generateReportNow() : Error trace "
								+ exc);
					}

					Logger.logMessage("InstantReportGenerator.generateReportNow() : file generated  "
							+ file + " converted pojo share list " + shareList);
					break;
				default:
					break;
				}

				if (errorCode == Constants.DATABASE_CONNECTION_SUCCESS
						&& file != null) {
					errorCode = Constants.JOB_PROCESSED_SUCCESSFULLY;
					Logger.logMessage("InstantReportGenerator.generateReportNow() : Report "
							+ file.getName()
							+ " created successfully, \n Please find it at "
							+ file.getAbsolutePath());

				}
			} else {

				errorCode = Constants.JOB_IN_WAITING_LIST;
				Logger.logMessage("InstantReportGenerator.generateReportNow() : Database connection error!"
						+ "\nAdded request to waiting queue! \nReport will be mailed once connected to database");
				job.setJobType(Constants.INSTANT_REPORT_EMAIL);
				((ReportRequest)job.getJob()).setFileOption(Constants.PDF_FILE_REPORT);
				JobManager.getTimedwaitJobHandler().addJobToList(job);
				PopupWindow.popup
						.createErrorPopup(
								"Database connection error!",
								"Not connected to database! \n Added request to waiting queue! \n Report will be mailed once connected to database!");
			}
			if (errorCode == Constants.JOB_REPORT_GENERATION_ERR
					|| errorCode == Constants.JOB_FAILED_DUE_TO_EXCEPTION) {
				Logger.logMessage("InstantReportGenerator.generateReportNow() : Severe code/database error : job "
						+ job + " ");
				PopupWindow.popup.createErrorPopup("Database or code error ",
						"Database or code error report to admin!");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return file;
	}
}
