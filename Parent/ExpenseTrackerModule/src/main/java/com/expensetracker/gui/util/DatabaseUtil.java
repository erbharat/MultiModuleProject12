package com.expensetracker.gui.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.jsp.jstl.core.Config;

import com.expensetracker.config.ResourceConfig;
import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.PurchaseReportData;
import com.expensetracker.gui.pojo.ShareReportData;
import com.expensetracker.logger.Logger;
import com.expensetracker.model.AggregateShare;
import com.expensetracker.model.Purchase;
import com.expensetracker.service.Service;
import com.mysql.jdbc.Connection;

public class DatabaseUtil {

	private static ResourceConfig config = ResourceConfig.getInstance();
	public static int checkDatabaseConnection() {
		int connCode = 0;
		try {
			connCode = openConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connCode;
	}

	public static List<PurchaseReportData> convertPurchaseModelToReportData(
			List<Purchase> list) {
		List<PurchaseReportData> purchaseDataList = new ArrayList<PurchaseReportData>();
		PurchaseReportData purchaseReport = null;
		ListIterator<Purchase> purchaseItr = list.listIterator();
		try {
			while (purchaseItr.hasNext()) {
				Purchase purchaseData = purchaseItr.next();
				purchaseReport = new PurchaseReportData();
				purchaseReport.setPurchaseId(purchaseData.getPurchaseId());
				purchaseReport.setUserName(purchaseData.getUserId());
				purchaseReport.setPurchaseType(purchaseData.getPurchaseType());
				if (purchaseData.getPurchaseType().equals(Constants.DAILY_PURCHASE_TYPE)) {
				purchaseReport.setItemList(Arrays.asList((purchaseData
						.getItemList().split(","))));
				} else {
				purchaseReport.setContributorList(Arrays.asList((purchaseData
						.getContributorList().split(","))));
				purchaseReport.setContributionList(Arrays.asList((purchaseData
						.getContributionList().split(","))));
				}
				purchaseReport.setDate(DateUtil.stringToDate(purchaseData.getPurchaseDate()));
				purchaseReport.setTotalAmount(purchaseData.getTotalAmount());
				purchaseDataList.add(purchaseReport);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return purchaseDataList;
	}

	public static List<ShareReportData> convertAggregateShareModelToShareData(
			List<AggregateShare> list) {

		List<ShareReportData> shareDataList = new ArrayList<ShareReportData>();
		ShareReportData aggregateShareReport = null;
		ListIterator<AggregateShare> aggregateShareItr = list.listIterator();
		List<ShareReportData> posAmntList = new ArrayList<ShareReportData>();
		List<ShareReportData> negAmntList = new ArrayList<ShareReportData>();
		try {
			while (aggregateShareItr.hasNext()) {
				AggregateShare shareData = aggregateShareItr.next();
				double amount = shareData.getCurrentShare();
				if (amount == 0) {
					aggregateShareReport = new ShareReportData();
					aggregateShareReport.setUserName(shareData.getUserName());
					aggregateShareReport.setAmount(amount);
					aggregateShareReport.getMemberFromList().add("None");
					aggregateShareReport.getAmountFromList().add("0");
					aggregateShareReport.getAmountToList().add("0");
					aggregateShareReport.getMemberToList().add("None");
					shareDataList.add(aggregateShareReport);
				} else if (amount < 0) {
					aggregateShareReport = new ShareReportData();
					aggregateShareReport.setUserName(shareData.getUserName());
					aggregateShareReport.setAmount(amount);
					aggregateShareReport.getMemberFromList().add("None");
					aggregateShareReport.getAmountFromList().add("0");
					shareDataList.add(aggregateShareReport);
					negAmntList.add(aggregateShareReport);
				} else {
					aggregateShareReport = new ShareReportData();
					aggregateShareReport.setUserName(shareData.getUserName());
					aggregateShareReport.setAmount(amount);
					aggregateShareReport.getAmountToList().add("0");
					aggregateShareReport.getMemberToList().add("None");
					shareDataList.add(aggregateShareReport);
					posAmntList.add(aggregateShareReport);
				}
			}

			populatePositiveAndNegativeLists(posAmntList, negAmntList);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return shareDataList;
	}

	private static void populatePositiveAndNegativeLists(
			List<ShareReportData> posAmntList, List<ShareReportData> negAmntList) {
		// TODO Auto-generated method stub

		int posListIndex = 0;
		int negListIndex = 0;
		int posListAmount = 0;
		int negListAmount = 0;
		int result = 0;

		boolean isResultZero = true;
		boolean isResultNegative = false;
		boolean isResultPositive = false;
		try {
			while (posAmntList.size() > 0) {

				if (isResultZero) {
					posListAmount = (int) posAmntList.get(posListIndex)
							.getAmount();
					negListAmount = (int) negAmntList.get(posListIndex)
							.getAmount();
				}

				if (isResultNegative) {
					posListAmount = (int) posAmntList.get(posListIndex)
							.getAmount();
				}

				if (isResultPositive) {
					negListAmount = (int) negAmntList.get(posListIndex)
							.getAmount();
				}

				result = posListAmount + negListAmount;

				if (result < 0) {
					posAmntList.get(posListIndex).getAmountFromList()
							.add("" + Math.abs(posListAmount));
					posAmntList.get(posListIndex).getMemberFromList()
							.add(negAmntList.get(negListIndex).getUserName());
					negAmntList.get(negListIndex).getAmountToList()
							.add("" + Math.abs(posListAmount));
					negAmntList.get(negListIndex).getMemberToList()
							.add(posAmntList.get(posListIndex).getUserName());
					posAmntList.remove(posListIndex);
					negListAmount = result;
					isResultPositive = false;
					isResultNegative = true;
					isResultZero = false;
				} else if (result > 0) {

					posAmntList.get(posListIndex).getAmountFromList()
							.add("" + Math.abs(negListAmount));
					posAmntList.get(posListIndex).getMemberFromList()
							.add(negAmntList.get(negListIndex).getUserName());
					negAmntList.get(negListIndex).getAmountToList()
							.add("" + Math.abs(negListAmount));
					negAmntList.get(negListIndex).getMemberToList()
							.add(posAmntList.get(posListIndex).getUserName());
					negAmntList.remove(negListIndex);
					posListAmount = result;
					isResultPositive = true;
					isResultNegative = false;
					isResultZero = false;

				} else {

					posAmntList.get(posListIndex).getAmountFromList()
							.add("" + Math.abs(posListAmount));
					posAmntList.get(posListIndex).getMemberFromList()
							.add(negAmntList.get(negListIndex).getUserName());
					negAmntList.get(negListIndex).getAmountToList()
							.add("" + Math.abs(posListAmount));
					negAmntList.get(negListIndex).getMemberToList()
							.add(posAmntList.get(posListIndex).getUserName());
					negAmntList.remove(negListIndex);
					posAmntList.remove(posListIndex);
					isResultPositive = false;
					isResultNegative = false;
					isResultZero = true;
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private static int openConnection() throws SQLException {
		int connStatus = Constants.DATABASE_CONNECTION_ERROR;
		
		try {
			
			Class.forName(config.getDriverName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			Logger.logMessage("DatabaseUtil.openConnection() : jdbc connection url : " + config.getDBConnectionURL());
			conn = (Connection) DriverManager.getConnection(config.getDBConnectionURL()
					, config.getDBUsername(),
					config.getDBPassword());
			connStatus = Constants.DATABASE_CONNECTION_SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return connStatus;
	}
}
