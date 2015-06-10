package com.expensetracker.service;

import java.sql.Connection;
import java.util.List;

import com.expensetracker.constants.Constants;
import com.expensetracker.dao.AggregateShareDao;
import com.expensetracker.dao.GenericDao;
import com.expensetracker.dao.DaoFactory;
import com.expensetracker.dao.IndividualShareDao;
import com.expensetracker.dao.PurchaseDao;
import com.expensetracker.dao.UserDao;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.UserUpdateRequest;
import com.expensetracker.gui.util.DateUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.model.AggregateShare;
import com.expensetracker.model.IndividualShare;
import com.expensetracker.model.Purchase;
import com.expensetracker.model.User;
import com.expensetracker.util.ConvertUtil;
import com.expensetracker.util.HibernateUtil;

public class Service {

	private static final Service service = new Service();

	public static Service getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/**
	 * User Login Authentication Check Up Method Start
	 * 
	 * @return
	 **/
	public boolean userAuthentication(String userName, String password) {

		boolean isAuthenticated = false;
		try {
			Logger.logMessage("Service.userAuthentication(..) : Authenticate user with username "
					+ userName + " and password " + password);
			if (userName != null) {
				User user = (User) DaoFactory.getDao(Constants.USER_DAO).read(
						userName);
				if (user != null) {
					if (password != null && user.getPassword().equals(password)) {
						isAuthenticated = true;
						Logger.logMessage("Service.userAuthentication(..) : Authentication successfull for user "
								+ user.getUserName());
					}
				}
			}
		} catch (Exception e) {
			Logger.logMessage("Service.userAuthentication(..) : Authentication failed for user "
					+ userName + " error trace " + e.getLocalizedMessage());
		}

		if (!isAuthenticated) {
			Logger.logMessage("Service.userAuthentication(..) : Authentication failed for user "
					+ userName);
		}

		return isAuthenticated;
	}

	/** User Login Authentication Check Up Method End **/

	/** User CRUD Operation Method Start : **/

	public Object readUser(String id) throws Exception {
		GenericDao userDao = DaoFactory.getDao(Constants.USER_DAO);
		User user = (User) userDao.read(id);
		return user;
	}

	public void deleteUser(String id) throws Exception {
		GenericDao userDao = DaoFactory.getDao(Constants.USER_DAO);
		userDao.delete(id);

	}

	public void updateUser(String id, String key) throws Exception {
		GenericDao userDao = DaoFactory.getDao(Constants.USER_DAO);
		userDao.update(id, key);
	}
	
	public void updateUserStatus(Job job) throws Exception {
		UserDao userDao = (UserDao)DaoFactory.getDao(Constants.USER_DAO);
		UserUpdateRequest request = (UserUpdateRequest)job.getJob();
		userDao.updateUserStatus(request.getUsername(), request.getStatus());
	}
	
	public void updateUser(Job job) {
		GenericDao userDao = DaoFactory.getDao(Constants.USER_DAO);
		com.expensetracker.gui.pojo.User guiUser = (com.expensetracker.gui.pojo.User) job
				.getJob();
		User user = ConvertUtil.convertUserPojoToModel(guiUser);
		userDao.update(user);
		Logger.logMessage("Service.saveUser(.) : user " + user.getUserName()
				+ " updated successfully in database.");
	}

	public void saveUser(Job job) throws Exception {
		Logger.logMessage("Service.saveUser(.) : trying to save job.");
		GenericDao userDao = DaoFactory.getDao(Constants.USER_DAO);
		com.expensetracker.gui.pojo.User guiUser = (com.expensetracker.gui.pojo.User) job
				.getJob();
		User user = ConvertUtil.convertUserPojoToModel(guiUser);
		userDao.save(user);
		Logger.logMessage("Service.saveUser(.) : user " + user.getUserName()
				+ " saved successfully in database.");
		addAggregateShare(job);
	}

	private void addAggregateShare(Job job) throws Exception {
		// Adding entry for the user to aggreagate share table.
		User user = ((UserDao) DaoFactory.getDao(Constants.USER_DAO))
				.read(((com.expensetracker.gui.pojo.User) job.getJob())
						.getUserName());
		AggregateShare share = new AggregateShare();
		share.setUserName(user.getUserName());
		share.setCurrentShare((double)0);
		(DaoFactory.getDao(Constants.AGGREGATE_SHARE_DAO)).save(share);
		Logger.logMessage("Service.addAggregateShare(.) : Aggregate share entry created successfully");
	}

	/** User CRUD Operation Method End : **/

	/** Purchase CRUD Operation Method Start : **/
	public Object readPurchase(Object id) throws Exception {
		GenericDao purchaseDao = DaoFactory.getDao(Constants.PURCHASE_DAO);
		Purchase purchase = (Purchase) purchaseDao.read(id);
		return purchase;
	}

	public void deletePurchase(String id) throws Exception {
		GenericDao purchaseDao = DaoFactory.getDao(Constants.PURCHASE_DAO);
		purchaseDao.delete(id);

	}

	public void updatePurchase(String id, String key) throws Exception {
		GenericDao purchaseDao = DaoFactory.getDao(Constants.PURCHASE_DAO);
		purchaseDao.update(id, key);
	}

	public void savePurchase(Job job) throws Exception {
		Logger.logMessage("Service.savePurchase(.) : Saving purchase details to database.");
		GenericDao purchaseDao = DaoFactory.getDao(Constants.PURCHASE_DAO);
		com.expensetracker.gui.pojo.Purchase guiPurchase = (com.expensetracker.gui.pojo.Purchase) job
				.getJob();
		Purchase purchase = ConvertUtil.convertPurchasePojoToModel(guiPurchase);
		purchaseDao.save(purchase);
		Logger.logMessage("Service.savePurchase(.) : Saved purchase details to database.");
		this.saveIndividualShare(purchase, guiPurchase);
	}

	/** Purchase CRUD Operation Method End : **/

	/** Get all User List Start : **/

	public List<User> getUserList() throws Exception {
		Logger.logMessage("Service.getUserList() : Getting list of users from database.");
		UserDao userDao = (UserDao) DaoFactory.getDao(Constants.USER_DAO);
		List<User> userList = userDao.getUserList();
		Logger.logMessage("Service.getUserList() : Returning list of users from database "
				+ userList);
		return userList;
	}

	/** Get all User List End : **/

	/** Get all Purchase List Start : **/

	public List<Purchase> getPurchaseReport(Job job) throws Exception {
		Logger.logMessage("Service.getPurchaseReport(.) : Getting purchase record from database.");
		PurchaseDao purchaseDao = (PurchaseDao) DaoFactory
				.getDao(Constants.PURCHASE_DAO);
		List<Purchase> purchaseList = purchaseDao.getPurchaseReport(job);
		if (purchaseList != null)
			Logger.logMessage("Service.getPurchaseReport(.) : Retreived purchase record from database, size of list = "
					+ purchaseList.size());
		else
			Logger.logMessage("Service.getPurchaseReport(.) : Error retreving purchase record from database.");
		return purchaseList;
	}

	/** Get all Purchase List End : **/

	/** Get all Share List Start : **/

	public List<AggregateShare> getShareReport(Job job) throws Exception {
		Logger.logMessage("Service.getShareReport(.) : Getting share record from database.");
		AggregateShareDao aggregateShareDao = (AggregateShareDao) DaoFactory
				.getDao(Constants.AGGREGATE_SHARE_DAO);
		List<AggregateShare> aggregateShareList = aggregateShareDao
				.getShareReport(job);
		if (aggregateShareList != null) 
			Logger.logMessage("Service.getPurchaseReport(.) : Retreived share record from database, size of list = " + aggregateShareList.size());
		else 
			Logger.logMessage("Service.getPurchaseReport(.) : Error retreving share record from database.");
		return aggregateShareList;
	}

	/** Get all Share List End : **/

	/** Get all IndividualShare calculation Start : **/
	private void saveIndividualShare(Purchase purchase,
			com.expensetracker.gui.pojo.Purchase guiPurchase) throws Exception {
		
		Logger.logMessage("Service.saveIndividualShare(..) : Saving individual share record into database.");

		IndividualShareDao individualShareDao = (IndividualShareDao) DaoFactory
				.getDao(Constants.INDIVIDUAL_SHARE_DAO);
		List<User> list = ((UserDao) DaoFactory.getDao(Constants.USER_DAO))
				.getActiveUserList();
		
		Logger.logMessage("Service.saveIndividualShare(..) : Active user list " + list);

		if (purchase.getPurchaseType().equals(Constants.DAILY_PURCHASE_TYPE)) {

			IndividualShare individualShare = new IndividualShare();
			individualShare.setPurchaseId(purchase);
			individualShare.setAmount(purchase.getTotalAmount());
			individualShare.setUserName(purchase.getUserId());
			individualShare.setDate(DateUtil.stringToDate(purchase.getPurchaseDate()));
			individualShareDao.save(individualShare);
			
			Logger.logMessage("Service.saveIndividualShare(..) : saved individual share for daily purchase into database " + individualShare);
			this.updateAggregateShare(purchase.getUserId(),
					purchase.getTotalAmount(), list);
		} else if (purchase.getPurchaseType().equals(Constants.BULK_PURCHASE_TYPE)) {
			List<String> usersList = guiPurchase.getContributorList();

			for (int i = 0; i < usersList.size(); i++) {
				IndividualShare individualShare = new IndividualShare();
				individualShare.setPurchaseId(purchase);
				individualShare.setAmount(Integer.parseInt(guiPurchase
						.getContributionList().get(i)));
				individualShare.setUserName(guiPurchase.getContributorList()
						.get(i));
				individualShare.setDate(DateUtil.stringToDate(purchase.getPurchaseDate()));
				individualShareDao.save(individualShare);
				Logger.logMessage("Service.saveIndividualShare(..) : saved individual share for bulk purchase into database " + individualShare);

				this.updateAggregateShare(
						guiPurchase.getContributorList().get(i), Double
								.parseDouble((guiPurchase.getContributionList()
										.get(i))), list);
			}
		} else if (purchase.getPurchaseType().equals(Constants.HOUSE_RENT_TYPE)) {
			List<User> allUsersList = ((UserDao) DaoFactory.getDao(Constants.USER_DAO))
					.getUserList();
			
			IndividualShare individualShare = new IndividualShare();
			individualShare.setPurchaseId(purchase);
			individualShare.setAmount(purchase.getTotalAmount());
			individualShare.setUserName(purchase.getUserId());
			individualShare.setDate(DateUtil.stringToDate(purchase.getPurchaseDate()));
			individualShareDao.save(individualShare);
			
			Logger.logMessage("Service.saveIndividualShare(..) : saved individual share for House rent into database " + individualShare);
			this.updateAggregateShare(purchase.getUserId(),
					purchase.getTotalAmount(), allUsersList);
		}
	}

	/** Get all IndividualShare calculation End : **/

	/**
	 * Get all AggregateShare calculation Start :
	 * 
	 * @param list
	 **/
	private void updateAggregateShare(String shopper, double amount,
			List<User> list) {
		Logger.logMessage("Service.updateAggregateShare(...) : Updating aggregate share for common purchase into database.");
		double individualShare = amount / list.size();
		try {

			for (User user : list) {

				AggregateShare   aggregateShare = (AggregateShare) ((AggregateShareDao) DaoFactory
						.getDao(Constants.AGGREGATE_SHARE_DAO)).read(user
						.getUserName());
				Logger.logMessage("Service.updateAggregateShare(...) : Retrived aggregate share " + aggregateShare + " for " + user + " from database ");
				if (user.getUserName().equals(shopper)) {
					double shopperAmount = aggregateShare.getCurrentShare();
					aggregateShare.setCurrentShare(shopperAmount
							+ (amount - individualShare));
					((AggregateShareDao) DaoFactory
							.getDao(Constants.AGGREGATE_SHARE_DAO))
							.update(aggregateShare);
					Logger.logMessage("Service.updateAggregateShare(...) : Updated aggregated share" + aggregateShare + " for " + user + " into database ");

				} else {
					double shopperAmount = aggregateShare.getCurrentShare();
					aggregateShare.setCurrentShare(shopperAmount
							- individualShare);
					((AggregateShareDao) DaoFactory
							.getDao(Constants.AGGREGATE_SHARE_DAO))
							.update(aggregateShare);
					
					Logger.logMessage("Service.updateAggregateShare(...) : Updated aggregated share" + aggregateShare + " for " + user + " into database ");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Get all AggregateShare calculation End : **/

	public int checkDatabaseConnection() {
		int errorCode = Constants.DATABASE_CONNECTION_SUCCESS;

		Connection conn = (Connection) HibernateUtil.getSessionFactory()
				.openSession().connection();
		if (conn == null) {
			
			errorCode = Constants.DATABASE_CONNECTION_ERROR;
		}
		
		Logger.logMessage("Service.checkDatabaseConnection() : database connection is " + conn);
		return errorCode;
	}
}
