package com.expensetracker.handler;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.PurchaseReportData;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.pojo.ShareReportData;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.util.DatabaseUtil;
import com.expensetracker.gui.util.ReportUtil;
import com.expensetracker.gui.util.UserUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.UserManager;
import com.expensetracker.model.AggregateShare;
import com.expensetracker.model.Purchase;
import com.expensetracker.service.Service;

public class TimedWaitJobHandler {
	private final static TimedWaitJobHandler timedWaitJobHandler = new TimedWaitJobHandler();
	private LinkedList<Job> waitingJobList = new LinkedList<Job>();
	private Timer timer = null;

	public void createAndStartTimer() {
		timer = new Timer();
		timer.schedule(new ProcessWaitingJobs(), new Date(),
				Constants.JOB_TIMER_INTERVAL);
		Logger.logMessage("TimedWaitJobHandler.createAndStartTimer() : Timer created and scheduled for time interval "
				+ Constants.JOB_TIMER_INTERVAL);
	}

	public static TimedWaitJobHandler getTimedwaitjobhandler() {
		return timedWaitJobHandler;
	}

	public LinkedList<Job> getWaitingJobList() {
		return waitingJobList;
	}

	public void addJobToList(Job job) {
		waitingJobList.add(job);
	}

	class ProcessWaitingJobs extends TimerTask {

		@Override
		public void run() {
			try {
				Logger.logMessage("TimedWaitJobHandler.run() : Process waiting list if not empty : "
						+ waitingJobList.size());
				if (DatabaseUtil.checkDatabaseConnection() == Constants.DATABASE_CONNECTION_SUCCESS) {
					for (Job job : waitingJobList) {
						if (processJob(job) == Constants.JOB_PROCESSED_SUCCESSFULLY) {
							waitingJobList.remove(job);
							Logger.logMessage("TimedWaitJobHandler.run Removing job from waiting list : "
									+ job.getJob()
									+ " size of list now : "
									+ +waitingJobList.size());
						}
					}
				}
			} catch (Exception e) {
				if (e instanceof InterruptedException) {
					timer.cancel();
				}
			}
		}

		private int processJob(Job job) {
			int retVal = Constants.JOB_IN_WAITING_LIST;
			try {
				switch (job.getJobType()) {
				case Constants.NEW_PROFILE:
					try {
						Service.getService().saveUser(job);
					} catch (Exception e) {
						retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
						Logger.logMessage("TimedWaitJobHandler.processJob(.) : "
								+ job + " " + e.getMessage());

					}
					break;

				case Constants.COMMON_PURCHASE_DETAIL:
					try {

						Service.getService().savePurchase(job);
					} catch (Exception e) {
						retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
						Logger.logMessage("TimedWaitJobHandler.processJob(.) : "
								+ job + " " + e.getMessage());
					}
					break;

				case Constants.UPDATE_PROFILE:
					try {

						Service.getService().updateUser(job);
					} catch (Exception e) {
						e.printStackTrace();
						retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
						Logger.logMessage("TimedWaitJobHandler.processJob(.) : "
								+ job + " error " + e.getMessage());
					}
					break;

				case Constants.RESET_PROFILE_PASSWORD:
					try {
						String data[] = (String[]) job.getJob();
						Service.getService().updateUser(data[0], data[1]);
					} catch (Exception e) {
						e.printStackTrace();
						retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
						Logger.logMessage("TimedWaitJobHandler.processJob(.) : "
								+ job + " error " + e.getMessage());
					}
					break;

				case Constants.INSTANT_REPORT_EMAIL:

					File file = null;
					switch (((ReportRequest) job.getJob()).getReportType()) {
					case Constants.PURCHASE_REPORT:
						List<Purchase> purchase = null;
						try {
							purchase = Service.getService().getPurchaseReport(
									job);
						} catch (Exception e) {
							retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
							Logger.logMessage("TimedWaitJobHandler.processJob(.) : "
									+ job + " " + e.getMessage());
						}
						List<PurchaseReportData> purchaseList = DatabaseUtil
								.convertPurchaseModelToReportData(purchase);
						file = ReportUtil.generatePurchaseReport(purchaseList,
								job);
						break;
					case Constants.SHARE_REPORT:
						List<AggregateShare> share = null;
						try {
							share = Service.getService().getShareReport(job);
						} catch (Exception e) {
							retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
							Logger.logMessage("TimedWaitJobHandler.processJob(.) : "
									+ job + " " + e.getMessage());
						}
						List<ShareReportData> shareList = DatabaseUtil
								.convertAggregateShareModelToShareData(share);
						file = ReportUtil.generateShareReport(shareList, job);
						break;
					default:
						break;
					}

					if (file != null) {
						Set<User> set = UserManager.getUserManager()
								.getUserList();
						List<String> emailIDList = UserUtil
								.userEmailIdList(set);
						Mail.sendMail(emailIDList, file);
						PopupWindow.popup
								.createInformationalPopup(
										"Success!",
										"Your Report "
												+ file.getName()
												+ " successfully generated and sent to your email id");
					}

					break;

				default:
					break;
				}

				if (retVal == Constants.JOB_IN_WAITING_LIST) {
					retVal = Constants.JOB_PROCESSED_SUCCESSFULLY;
					Logger.logMessage("TimedWaitJobHandler.processJob (.): Job processed successfully from waiting list "
							+ job);
				}
			} catch (Exception exc) {
				retVal = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
				Logger.logMessage("TimedWaitJobHandler.processJob(.) : Exception in processing Job from timed wait list "
						+ job + " " + exc.getMessage());
			}

			return retVal;
		}
	}
}
