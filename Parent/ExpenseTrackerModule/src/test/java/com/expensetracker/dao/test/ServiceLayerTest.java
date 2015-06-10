package com.expensetracker.dao.test;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.util.DateUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.model.Purchase;
import com.expensetracker.service.Service;

public class ServiceLayerTest {

	@Before
	public void setUp() throws Exception {
		Logger.initializeLogger();
	}

	@After
	public void tearDown() throws Exception {
		Logger.closeLogger();
	}

	@Test
	public void testGetPurchaseList() {
		
		String reportType = Constants.PURCHASE_REPORT;
		String month = "Feb";
		String year = "2015";
		String fromDate = "2015-03-01";
		String toDate = "2015-03-31";
		String fileOption = Constants.PDF_FILE_REPORT;
		ReportRequest request = new ReportRequest(reportType, month, year, fromDate, toDate, fileOption);
		Job job = new Job(Constants.INSTANT_REPORT_DOWNLOAD, request);
		try {
			List<Purchase> list = Service.getService().getPurchaseReport(job);
			Assert.assertNotNull(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
