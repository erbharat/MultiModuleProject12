package com.expensetracker.gui.util.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.expensetracker.gui.util.DateUtil;

public class DateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testValidateDate() {
		
		
		Date date = null;
		Date date1 = null;
		Date date2 = null;
		Date date3 = null;
		Date date4 = null;
		Date date5 = null;
		
		Calendar cal = new GregorianCalendar(2015, 4, 2);
		date = cal.getTime();
		cal = new GregorianCalendar(2015, 3, 6);
		date1 = cal.getTime();
		
		cal = new GregorianCalendar(2015, 3, 5);
		date2 = cal.getTime();
		cal = new GregorianCalendar(2015, 2, 2);
		date3 = cal.getTime();
		cal = new GregorianCalendar(2015, 0, 9);
		date4 = cal.getTime();
		cal = new GregorianCalendar(2014, 12, 14);
		date5 = cal.getTime();
		
		Assert.assertFalse(DateUtil.validateDate(date));
		Assert.assertFalse(DateUtil.validateDate(date1));
		Assert.assertFalse(DateUtil.validateDate(date2));
		Assert.assertTrue(DateUtil.validateDate(date3));
		Assert.assertTrue(DateUtil.validateDate(date4));
		Assert.assertFalse(DateUtil.validateDate(date5));
		
		cal = new GregorianCalendar(2015, 04, 14);
		Date toDate1 = cal.getTime();
		
		cal = new GregorianCalendar(2015, 03, 14);
		Date fromDate1 = cal.getTime();
		
		cal = new GregorianCalendar(2015, 02, 05);
		Date toDate2 = cal.getTime();
		
		cal = new GregorianCalendar(2015, 01, 14);
		Date fromDate2 = cal.getTime();
		
		Assert.assertFalse(DateUtil.validateDate(toDate1, fromDate1));
		Assert.assertTrue(DateUtil.validateDate(toDate2, fromDate2));
	}

}
