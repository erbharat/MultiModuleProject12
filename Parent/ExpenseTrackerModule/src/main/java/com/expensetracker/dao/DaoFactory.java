package com.expensetracker.dao;

import com.expensetracker.constants.Constants;

public class DaoFactory {
	/* Factory of Data Access Object */
	public static GenericDao getDao(String daoObject) {
		GenericDao dao = null;
		if (daoObject.equalsIgnoreCase(Constants.USER_DAO)) {
			dao = UserDao.getUserDao();
		} else if (daoObject.equalsIgnoreCase(Constants.PURCHASE_DAO)) {
			dao = PurchaseDao.getPurchaseDao();
		} else if (daoObject.equalsIgnoreCase(Constants.INDIVIDUAL_SHARE_DAO)) {
			dao = IndividualShareDao.getIndividualShareDao();
		} else if (daoObject.equalsIgnoreCase(Constants.AGGREGATE_SHARE_DAO)) {
			dao = AggregateShareDao.getAggregateShareDao();
		} else {
			dao = null;
		}
		return dao;
	}
}
