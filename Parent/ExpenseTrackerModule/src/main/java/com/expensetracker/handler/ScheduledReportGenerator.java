package com.expensetracker.handler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;

public class ScheduledReportGenerator {
	private final static ScheduledReportGenerator schdldReportGenerator = new ScheduledReportGenerator();
	private Timer timer = null;

	public void createAndStartTimer() {
		timer = new Timer();
		timer.schedule(new ProcessScheduleReport(), new Date(),
				Constants.REPORT_TIMER_INTERVAL);
	}

	public static ScheduledReportGenerator getScheduledReportGenerator() {
		return schdldReportGenerator;
	}


	class ProcessScheduleReport extends TimerTask {

		@Override
		public void run() {
			try {

			} catch (Exception e) {
				if (e instanceof InterruptedException) {
					timer.cancel();
				}
			}
		}

		private void processJob(Job job) {
			switch (job.getJobType()) {
			case Constants.NEW_PROFILE:
				// TODO - Call profile related service save method.
				break;
				
			case Constants.COMMON_PURCHASE_DETAIL:
				// TODO - Call purchase related service save method.
				break;
				
			case Constants.INSTANT_REPORT_EMAIL:
				// TODO 
				// 1- Call instant report related service method.
				// 2- Get list of records for from date till yesterday.
				// 3- Call generateReport method of ReportUtil and pass this list.
				// 4- Send the generated report in mail to the requested id.
				break;

			default:
				break;
			}
		}
	}
}
