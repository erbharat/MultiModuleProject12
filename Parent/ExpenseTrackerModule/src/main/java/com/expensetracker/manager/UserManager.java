package com.expensetracker.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.util.DatabaseUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.service.Service;
import com.expensetracker.util.ConvertUtil;

public class UserManager {

	private Set<User> userList = new HashSet<User>();
	private Map<String, User> userMap = new HashMap<String, User>();
	private static UserManager userManager = new UserManager();

	/**
	 * @return the userList
	 */
	public Set<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}

	/**
	 * @return the userManager
	 */
	public static UserManager getUserManager() {
		return userManager;
	}

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public static void setUserManager(UserManager userManager) {
		UserManager.userManager = userManager;
	}

	public User getUser(String username) {
		return userMap.get(username);
	}

	public int addUser(User user) {
		int jobStatus = Constants.ERROR;
		Job job = new Job(Constants.NEW_PROFILE, user);
		jobStatus = JobManager.getInstantJobHandler().processJob(job);

		userList.add(user);
		userMap.put(user.getUserName(), user);
		if (jobStatus == Constants.JOB_PROCESSED_SUCCESSFULLY) {
			Logger.logMessage("UserManager.addUser(.) : Added user into userlist cache "
					+ user);
		}
		return jobStatus;
	}

	public void removeUser(User user) {
		Job job = new Job(Constants.REMOVE_USER, user);
		JobManager.getInstantJobHandler().processJob(job);
		userList.remove(user);
		userMap.put(user.getUserName(), user);
		Logger.logMessage("UserManager.removeUser(.) : Removed user into userlist cache "
				+ user);
	}

	public void addAll(Set<User> set) {
		userList.addAll(set);
	}

	public void removeAll() {
		userList.clear();
	}

	public void populateUserListFromDatabase() {
		// TODO Service call for user info to populate userlist.
		List<com.expensetracker.model.User> modelUserList = null;
		Logger.logMessage("UserManager.populateUserListFromDatabase() : Populating user list cache..");
		try {
			modelUserList = Service.getService().getUserList();
			List<User> list = ConvertUtil.modelToPojo(modelUserList);
			if (list != null) {
				userList.addAll(list);
				populateUserMap(list);
			} else {
				Logger.logMessage("UserManager.populateUserListFromDatabase() : No data received from database for populating userlist cache");
			}
		} catch (Exception e) {
			Logger.logMessage("UserManager.populateUserListFromDatabase() : Error in getting list of users from database for populating userlist cache "
					+ e);
		}
	}

	private void populateUserMap(List<User> list) {
		// TODO Auto-generated method stub
		for (User user : list) {
			userMap.put(user.getUserName(), user);
		}

	}

	public boolean authenticateUser(String username, String password) {
		// TODO Auto-generated method stub
		boolean isAuthenticated = false;
		boolean db_error = true;
		Logger.logMessage("UserManager.authenticateUser(..) : authenticate user "
				+ username);
		if (DatabaseUtil.checkDatabaseConnection() == Constants.DATABASE_CONNECTION_SUCCESS) {
			isAuthenticated = Service.getService().userAuthentication(username,
					password);
			if (userList.size() == 0) {
				populateUserListFromDatabase();
			}
			db_error = false;
		} else {
			PopupWindow.popup.createInformationalPopup("Login Error",
					"Not connected to database, database server is down.");
			Logger.logMessage("UserManager.authenticateUser(..) : database connection not alive authenticating from in memory cache "
					+ username);
/*			User user = userMap.get(username);
			if (user != null) {
				isAuthenticated = true;
				db_error = false;
			}*/
		}
		if (!isAuthenticated && !db_error) {
			PopupWindow.popup.createInformationalPopup("Login Error",
					"Username/Password don not match");
		}
		return isAuthenticated;
	}
}
