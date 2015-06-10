package com.expensetracker.manager;

import java.util.List;

import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.util.PersistenceUtil;
import com.expensetracker.handler.InstantJobHandler;
import com.expensetracker.handler.TimedWaitJobHandler;
import com.expensetracker.logger.Logger;

public class JobManager {

	private final static JobManager manager = new JobManager();
	private final static InstantJobHandler instantJobHandler = InstantJobHandler
			.getInstantjobhandler();
	private final static TimedWaitJobHandler timedWaitJobHandler = TimedWaitJobHandler
			.getTimedwaitjobhandler();

	public static JobManager getJobManager() {
		return manager;
	}

	public static InstantJobHandler getInstantJobHandler() {
		return instantJobHandler;
	}

	public static TimedWaitJobHandler getTimedwaitJobHandler() {
		return timedWaitJobHandler;
	}

	public int addJob(Job job) {
		int errorType = 0;
		errorType = instantJobHandler.processJob(job);
		Logger.logMessage("JobManager.addJob(.) : added job to job handler for processing.");
		return errorType;
	}

	public void removeJob(Job job) {
		// TODO
	}

	public void saveObjectToDisk() {
		Logger.logMessage("JobManager.saveObjectToDisk() : Serializing object to disk.");
		PersistenceUtil.saveObject(timedWaitJobHandler.getWaitingJobList());
	}
	
	public void readObjectFromDisk() {
		Logger.logMessage("JobManager.readObjectFromDisk() : Reading serialized object from disk.");
		List<Job> list = (List<Job>)PersistenceUtil.readObject();
		if (list != null) {
			timedWaitJobHandler.getWaitingJobList().addAll(list);
		}
		Logger.logMessage("JobManager.readObjectFromDisk() : Serialized object " + list);
	}
}
