package com.expensetracker.handler;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.pojo.UserUpdateRequest;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.util.DatabaseUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.JobManager;
import com.expensetracker.service.Service;

public class InstantJobHandler {

	private final static InstantJobHandler instantJobHandler = new InstantJobHandler();

	public int processJob(Job job) {
		int erroCode = DatabaseUtil.checkDatabaseConnection();
		Logger.logMessage("InstantJobHandler.processJob(.) : database connection is alive ? "
				+ erroCode);
		if (erroCode == Constants.DATABASE_CONNECTION_SUCCESS) {
			switch (job.getJobType()) {
			case Constants.NEW_PROFILE:
				try {
					Service.getService().saveUser(job);
				} catch (Exception e) {
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
					e.printStackTrace();
				}
				break;

			case Constants.COMMON_PURCHASE_DETAIL:
				try {

					Service.getService().savePurchase(job);
				} catch (Exception e) {
					e.printStackTrace();
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
				}
				break;

			case Constants.UPDATE_PROFILE:
				try {

					Service.getService().updateUser(job);
				} catch (Exception e) {
					e.printStackTrace();
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
				}
				break;

			case Constants.RESET_PROFILE_PASSWORD:
				try {
					UserUpdateRequest data = (UserUpdateRequest) job.getJob();
					Service.getService().updateUser(data.getUsername(),
							data.getNewPassword());
				} catch (Exception e) {
					e.printStackTrace();
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
				}
				break;

			case Constants.ACTIVATE_USER:
				try {

					Service.getService().updateUserStatus(job);
				} catch (Exception e) {
					e.printStackTrace();
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
				}
				break;

			case Constants.DEACTIVATE_USER:
				try {
					Service.getService().updateUserStatus(job);
				} catch (Exception e) {
					e.printStackTrace();
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
				}
				break;
				
			case Constants.REMOVE_USER:
				try {
					Service.getService().deleteUser(((User)job.getJob()).getUserName());
				} catch (Exception e) {
					e.printStackTrace();
					erroCode = Constants.JOB_FAILED_DUE_TO_EXCEPTION;
					Logger.logMessage("InstantJobHandler.processJob(.) : "
							+ job + " error " + e.getMessage());
				}
				break;
			default:
				break;
			}
			if (erroCode == Constants.DATABASE_CONNECTION_SUCCESS) {
				erroCode = Constants.JOB_PROCESSED_SUCCESSFULLY;
				Logger.logMessage("InstantJobHandler.processJob(.) : job processed, errorcode : "
						+ erroCode + " : " + job);
				PopupWindow.popup.createInformationalPopup("Success!",
						"Job processed successfully.");
			}
		} else {
			erroCode = Constants.JOB_IN_WAITING_LIST;
			Logger.logMessage("InstantJobHandler.processJob(.) : Instant job process failed due to database connection error  "
					+ job + " adding to timed wait process queue.");
			JobManager.getTimedwaitJobHandler().addJobToList(job);
			PopupWindow.popup
					.createErrorPopup("Database connection error!",
							"Not connected to database! \n Added request to waiting queue!.");
		}
		if (erroCode == Constants.JOB_FAILED_DUE_TO_EXCEPTION) {
			PopupWindow.popup.createErrorPopup("Severe error!",
					"Report to admin related to this error.");
		}

		return erroCode;
	}

	public static InstantJobHandler getInstantjobhandler() {
		return instantJobHandler;
	}
}
