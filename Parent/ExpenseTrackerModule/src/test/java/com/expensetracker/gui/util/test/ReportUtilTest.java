package com.expensetracker.gui.util.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.PurchaseReportData;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.pojo.ShareReportData;
import com.expensetracker.gui.util.DatabaseUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.model.AggregateShare;

public class ReportUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	public static void testGeneratePurchaseReport() {
		List<PurchaseReportData> reportList = generatePurchaseReportList();
		/*ReportRequest request = new ReportRequest(Constants.PURCHASE_REPORT, "MARCH", "2015", new Date(), new Date(), "PDF");
		Job job = new Job(Constants.INSTANT_REPORT_DOWNLOAD,request);
		
		File f = ReportUtil.generatePurchaseReport(reportList, job);
		//Assert.assertNotNull(f);
*/	}
	

	@Test
	public  void testGeneratePurchaseReport1() {
		List<PurchaseReportData> reportList = generatePurchaseReportList();
		/*ReportRequest request = new ReportRequest(Constants.PURCHASE_REPORT, "MARCH", "2015", new Date()., new Date(), "PDF");
		Job job = new Job(Constants.INSTANT_REPORT_DOWNLOAD,request);
		
		File f = ReportUtil.generatePurchaseReport(reportList, job);
		Assert.assertNotNull(f);*/
	}
	
	@Test
	public  void testGenerateShareReport() {
		List<ShareReportData> shareList = generateShareReportList();
		/*ReportRequest request = new ReportRequest(Constants.SHARE_REPORT, "MARCH", "2015", new Date(), new Date(), "PDF");
		Job job = new Job(Constants.INSTANT_REPORT_DOWNLOAD,request);
		
		File f = ReportUtil.generateShareReport(shareList, job);
		Assert.assertNotNull(f);*/
	}

	private List<ShareReportData> generateShareReportList() {
		List<AggregateShare> share = new ArrayList<AggregateShare>();
		
		AggregateShare share1 = new AggregateShare();
		share1.setUserName("BHARAT");
		share1.setCurrentShare(300);
		
		AggregateShare share2 = new AggregateShare();
		share2.setUserName("DEVENDRA");
		share2.setCurrentShare(-100);
		
		AggregateShare share3 = new AggregateShare();
		share3.setUserName("AAYUSH");
		share3.setCurrentShare(-100);
		
		AggregateShare share4 = new AggregateShare();
		share4.setUserName("PRATAP");
		share4.setCurrentShare(0);
		
		AggregateShare share5 = new AggregateShare();
		share5.setUserName("AMRISH");
		share5.setCurrentShare(-100);
		
		share.add(share1);
		share.add(share2);
		share.add(share3);
		share.add(share4);
		share.add(share5);
				
		return DatabaseUtil.convertAggregateShareModelToShareData(share);
	}

	private static List<PurchaseReportData> generatePurchaseReportList() {
		// TODO Auto-generated method stub
		
		int purchaseId = 1;
		String userName = "Bharat";
		String purchaseType = "DAILY";
		List<String> itemList = new LinkedList<String> ();
		List<String> contributorList = new LinkedList<String> ();
		List<String> contributionList = new LinkedList<String> ();//
		itemList.add("Milk");
		itemList.add("Internet");
		contributorList.add("None");
		contributionList.add("None");
		Date date = new Date();
		double totalAmount = 1000D;
		PurchaseReportData data1 = new PurchaseReportData(purchaseId, userName, purchaseType, itemList, contributorList, contributionList, date, totalAmount);
		
		purchaseId = 2;
		userName = "Bharat";
		purchaseType = "BULk";
		List<String> itemList1 = new LinkedList<String> ();
		List<String> contributorList1 = new LinkedList<String> ();
		List<String> contributionList1 = new LinkedList<String> ();
		itemList1.add("Too Many");
		contributorList1.add("Bharat");
		contributorList1.add("Amrish");
		contributionList1.add("1000");
		contributionList1.add("700");
		date = new Date();
		totalAmount = 400D;
		PurchaseReportData data2 = new PurchaseReportData(purchaseId, userName, purchaseType, itemList1, contributorList1, contributionList1, date, totalAmount);
		
		purchaseId = 3;
		userName = "Devendra";
		purchaseType = "DAILY";
		List<String> itemList2 = new LinkedList<String> ();
		List<String> contributorList2 = new LinkedList<String> ();
		List<String> contributionList2 = new LinkedList<String> ();
		itemList2.add("Vegetable");
		contributorList2.add("None");
		contributionList2.add("None");
		date = new Date();
		totalAmount = 899.0D;
		PurchaseReportData data3 = new PurchaseReportData(purchaseId, userName, purchaseType, itemList2, contributorList2, contributionList2, date, totalAmount);
		
		purchaseId = 1;
		userName = "Bharat";
		purchaseType = "DAILY";
		List<String> itemList3 = new LinkedList<String> ();
		List<String> contributorList3 = new LinkedList<String> ();
		List<String> contributionList3 = new LinkedList<String> ();
		itemList3.add("Milk");
		itemList3.add("Electricity");
		contributorList3.add("None");
		contributionList3.add("None");
		date = new Date();
		totalAmount = 897.98D;
		PurchaseReportData data4 = new PurchaseReportData(purchaseId, userName, purchaseType, itemList3, contributorList3, contributionList3, date, totalAmount);
		
		List<PurchaseReportData> list = new LinkedList<PurchaseReportData>();
		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		
		return list;
	}

	//@Test
	public  void testLogRotation() {
		boolean flag = true;
		File f = new File(Constants.LOG_FILE_PATH);
		Logger.initializeLogger();
		while (flag) {

			Logger.logMessage("This is to test the log rotation functionality");
			try {
				Assert.assertEquals(3, f.list().length);
				flag = false;
			} catch (Error err) {
				
			}
		}
	}
}
