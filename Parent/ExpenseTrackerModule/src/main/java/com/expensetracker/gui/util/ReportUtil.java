package com.expensetracker.gui.util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.PurchaseReportData;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.pojo.ShareReportData;
import com.expensetracker.logger.Logger;

public class ReportUtil {
	private static StyleBuilder columnTitleStyle = null;
	private static StyleBuilder boldCenteredStyle = null;
	static {
		StyleBuilder boldStyle = stl.style().bold();

		boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(
				HorizontalAlignment.CENTER);

		columnTitleStyle = stl.style(boldCenteredStyle)
				.setBorder(stl.pen1Point())
				.setBackgroundColor(Color.LIGHT_GRAY);
	}

	public static File generatePurchaseReport(List<PurchaseReportData> list,
			Job job) {

		TextColumnBuilder<String> usernameCol = col.column("Member Username",
				"userName", type.stringType());
		TextColumnBuilder<Integer> purchaseIdCol = col.column("Purchase Id",
				"purchaseId", type.integerType());
		TextColumnBuilder<String> purchaseTypeCol = col.column("Purchase Type",
				"purchaseType", type.stringType());
		TextColumnBuilder<List> itemListCol = col.column("ItemList",
				"itemList", type.listType());
		TextColumnBuilder<List> contributorListCol = col.column(
				"Contributor List", "contributorList", type.listType());
		TextColumnBuilder<List> contributionListCol = col.column(
				"Contribution List", "contributionList",type.listType());
		TextColumnBuilder<Date> dateCol = col.column("Date", "date",
				type.dateType());
		TextColumnBuilder<Double> totalAmountCol = col.column("Total Amount", "totalAmount",
				type.doubleType());
		 
					JasperReportBuilder report = DynamicReports
					.report()
					.setColumnTitleStyle(columnTitleStyle)
					.highlightDetailEvenRows()
					.columns(usernameCol, purchaseIdCol, purchaseTypeCol,
							itemListCol, contributorListCol, contributionListCol,
							dateCol, totalAmountCol)
					.title(cmp.text(
							"Expense Tracker Purchase Report "
									+ ((ReportRequest) job.getJob()).getMonth()
									+ "-"
									+ ((ReportRequest) job.getJob()).getYear())
							.setStyle(boldCenteredStyle))
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					.setDataSource(createDataSource(list));
	

		return createFile(report, job); 
	}

	public static File generateShareReport(List<ShareReportData> list, Job job) {
		TextColumnBuilder<String> usernameCol = col.column("Member Username",
				"userName", type.stringType());
		TextColumnBuilder<Double> amountCol = col.column("Amount",
				"amount", type.doubleType());
		TextColumnBuilder<List> memberToListCol = col.column("To Member List",
				"memberToList", type.listType());
		TextColumnBuilder<List> amountToListCol = col.column("To Amount List",
				"amountToList", type.listType());
		TextColumnBuilder<List> memberFromListCol = col.column(
				"From Member List", "memberFromList", type.listType());
		TextColumnBuilder<List> amountFromListCol = col.column(
				"From Amount List", "amountFromList", type.listType());
		JasperReportBuilder report = DynamicReports
				.report()
				.setColumnTitleStyle(columnTitleStyle)
				.highlightDetailEvenRows()
				.columns(usernameCol, amountCol, memberToListCol,
						amountToListCol, memberFromListCol, amountFromListCol)
				.title(cmp.text(
						"Expense Tracker Share Report "
								+ ((ReportRequest) job.getJob()).getMonth()
								+ "-"
								+ ((ReportRequest) job.getJob()).getYear())
						.setStyle(boldCenteredStyle))
				.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
				.setDataSource(createDataSource(list));

		return createFile(report, job);
	}

	private static File createFile(JasperReportBuilder report, Job job) {
		// TODO Auto-generated method stub
		File file = null;
		FileOutputStream fout = null;
		try {

			switch (((ReportRequest) job.getJob()).getFileOption()) {
			case "PDF":
				file = new File(Constants.REPORT_FILE_PATH + "EXPENSE TRACKER " + ((ReportRequest) job.getJob()).getReportType() + "("
						+ ((ReportRequest) job.getJob()).getMonth() + ", "
						+ ((ReportRequest) job.getJob()).getYear() + ").pdf");
				fout = new FileOutputStream(file);
				report.toPdf(fout);
				break;

			case "DOCX":
				file = new File(Constants.REPORT_FILE_PATH + "EXPENSE TRACKER " + ((ReportRequest) job.getJob()).getReportType() + "("
						+ ((ReportRequest) job.getJob()).getMonth() + ", "
						+ ((ReportRequest) job.getJob()).getYear() + ").docx");
				fout = new FileOutputStream(file);
				report.toDocx(fout);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.logMessage("ReportUtil.createFile(..) : Error trace " + e);
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	private static JRDataSource createDataSource(List<?> list) {
		// TODO Auto-generated method stub
		return  new JRBeanCollectionDataSource(list);
	}
}
