package com.expensetracker.gui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.expensetracker.logger.Logger;

public class DateUtil {

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-mm-dd");

	public static Date stringToDate(String date) {
		Date returnDate = null;
		try {
			returnDate = (Date) formatter.parseObject(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnDate;
	}

	public static String dateToString(Date date) {
		return formatter.format(date);
	}

	public static Date getDateForSpecificDate(int date, int month, int year) {

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		Date date1 = cal.getTime();
		return date1; //stringToDate(dateToString(cal.getTime()));
	}

	public static Date getLastDateofMonth(int date, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);

		Date lastDate = cal.getTime();
		cal.setTime(lastDate);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();//stringToDate(dateToString(cal.getTime()));
	}

	public static Date getAsDate(LocalDate localDate) {
		if (localDate != null) {
			return Date.from(localDate.atStartOfDay()
					.atZone(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private static int dateRestriction = 6;
	private static int monthRestriction = 1;
	private static int yearRestriction = 2015;

	public static boolean validateDate(Date date) {

		Date currentDate = new Date();
		Date restrictedDate = null;
		int rDdate = 0;
		int rMonth = 0;
		int rYear = 0;

		if (currentDate.getDate() <= dateRestriction) {
			rDdate = 1;
		} else {
			rDdate = currentDate.getDate() - dateRestriction;
		}

		if (currentDate.getMonth() <= monthRestriction) {
			rMonth = 0;
		} else {
			rMonth = currentDate.getMonth() - monthRestriction;
		}

		rYear = yearRestriction;
		Calendar cal = new GregorianCalendar(rYear, rMonth, rDdate);
		restrictedDate = cal.getTime();
		if (date.compareTo(currentDate) < 0
				&& date.compareTo(restrictedDate) >= 0) {
			Logger.logMessage("DateUtil.validateDate(.) : Dates compared and result is " + true + " for dates: date -> " + date + " and current date -> " + currentDate + " and restricted date -> "+ restrictedDate);
			return true;
		}
		Logger.logMessage("DateUtil.validateDate(.) : Dates compared and result is " + false + " for dates: date -> " + date + " and current date -> " + currentDate + " and restricted date -> "+ restrictedDate);
		return false; 

	}

	public static boolean validateDate(Date toDate, Date fromDate) {
		
		if (validateDate(fromDate) && validateDate(toDate) && (fromDate.compareTo(toDate) < 0)) {
			Logger.logMessage("DateUtil.validateDate(..) : Dates compared and result is " + true + " for dates: toDate -> " + toDate + " and fromDate -> " + fromDate);
			return true;
		}
		Logger.logMessage("DateUtil.validateDate(..) : Dates compared and result is " + false + " for dates: toDate -> " + toDate + " and fromDate -> " + fromDate);
		return false;

	}
}
