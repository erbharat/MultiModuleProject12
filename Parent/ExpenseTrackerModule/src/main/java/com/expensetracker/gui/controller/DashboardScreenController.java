package com.expensetracker.gui.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;

import org.controlsfx.control.CheckComboBox;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.Purchase;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.pojo.User;
import com.expensetracker.gui.pojo.UserUpdateRequest;
import com.expensetracker.gui.popup.PopupWindow;
import com.expensetracker.gui.screen.EditProfileScreen;
import com.expensetracker.gui.screen.ResetPasswordScreen;
import com.expensetracker.gui.screen.ScreenConstant;
import com.expensetracker.gui.screen.ViewProfileScreen;
import com.expensetracker.gui.util.ControllerUtil;
import com.expensetracker.gui.util.DatabaseUtil;
import com.expensetracker.gui.util.DateUtil;
import com.expensetracker.gui.util.ImageUtil;
import com.expensetracker.gui.util.UserUtil;
import com.expensetracker.handler.Mail;
import com.expensetracker.logger.Logger;
import com.expensetracker.manager.JobManager;
import com.expensetracker.manager.ReportManager;
import com.expensetracker.manager.UserManager;
import com.expensetracker.model.AggregateShare;
import com.expensetracker.service.Service;
import com.expensetracker.util.ConvertUtil;

public class DashboardScreenController implements Initializable {

	/**
	 * @return the memberTxt
	 */
	public TextField getMemberTxt() {
		return memberTxt;
	}

	@FXML
	private Label usernameLabel, memberLabel, checkboxLabel,
			contributorListLabel, amountSharedLabel, username, amount,
			userLabelText, amountLabelText;

	@FXML
	private TextField totalAmountText, memberTxt, individualShareTxt, otherDetailText;

	@FXML
	private ComboBox<String> purchaseTypeComboBox, reportTypeCombo,
			downloadCombo, emailCombo, monthCombo, yearCombo, userCombo,
			operationCombo;

	@FXML
	private Button savePurchaseDetailBtn, resetBtn, downloadBtn, emailBtn,
			viewUserBtn, activateUserBtn, deactivateUserBtn, deleteUserBtn,
			editUserBtn, resetPasswordBtn, creditorPieChart, debtorPieChart;

	@FXML
	private CheckBox milkCurdCheckBox, kitchenItemsCheckBox,
			vegetablesOrDalCheckBox, othersCheckBox;

	@FXML
	private DatePicker dateDatePicker, from, to;

	@FXML
	private Tooltip multipleChoiceTip, individualShareTip;

	@FXML
	private StackPane manageMemberStackPane;

	@FXML
	private PieChart pieChart;

	final private String CREDITOR = "CREDITOR";
	final private String DEBTOR = "DEBTOR";

	public StackPane getManageMemberStackPane() {
		return manageMemberStackPane;
	}

	/*
	 * private final String Constants.DAILY_PURCHASE_TYPE = "DAILY PURCHASE";
	 * private final String Constants.BULK_PURCHASE_TYPE = "BULK PURCHASE";
	 */
	private String itemsList = null;
	@FXML
	private CheckComboBox<String> contributorListCombo, othersDetailCombo;

	public void initialize(URL location, ResourceBundle resources) {
		Logger.logMessage("DashboardScreenController.initialize(..) : Initializing dashboard screen fields..");
		initializePurchaseType();
		initailzeReportType();
		intializeReportFormat();
		intializeOthersList();
		intializeMonthAndYear();
		intializeMemberList();

		/*
		 * for (final PieChart.Data data : pieChart.getData()) {
		 * data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent e) {
		 * userLabel.setText(data.getName()); expenseLabel.setText("" +
		 * data.getPieValue()); } }); }
		 */

		contributorListCombo.setOnMouseClicked(new EventHandler<Event>() {

			public void handle(Event event) {
				intializeContibutorList();
			}
		});

		contributorListCombo.getCheckModel().getCheckedItems()
				.addListener(new ListChangeListener<String>() {
					public void onChanged(
							ListChangeListener.Change<? extends String> c) {
						onContributionSelection();
					}
				});

		othersDetailCombo.getCheckModel().getCheckedItems()
				.addListener(new ListChangeListener<String>() {
					public void onChanged(
							ListChangeListener.Change<? extends String> c) {
						onOthersSelection(c);
					}
				});
	}

	// *****************OPERATION BASED ON EVENTS *****************/
	@FXML
	public void save(ActionEvent event) {
		if (event.getSource() == savePurchaseDetailBtn) {
			Logger.logMessage("DashboardScreenController.save(.) : Saving purchasing details.");
			Purchase detail = createPurchaseDetail();
			if (detail != null) {
				Logger.logMessage("DashboardScreenController.save(.) :Purchase details created sucessfully.."
						+ detail);
				Job job = new Job(Constants.COMMON_PURCHASE_DETAIL, detail);
				int ret = JobManager.getInstantJobHandler().processJob(job);
				if (ret == Constants.JOB_PROCESSED_SUCCESSFULLY) {
					PopupWindow.popup.createInformationalPopup("Success!",
							"Purchase detail saved successfully");

				}
			}

		} else if (event.getSource() == resetBtn) {
			PopupWindow.popup.createInformationalPopup(
					"Reset purchase window fields",
					"Clearing purchase tab fields!");
		}
		clear();
	}

	@FXML
	public void download(ActionEvent event) {
		Job job = validateReportInput(Constants.INSTANT_REPORT_DOWNLOAD);
		File file = null;
		if (job != null) {
			file = ReportManager.getInstantReportGenerator().generateReportNow(
					job);
		}
		if (file != null) {
			Logger.logMessage("DashboardScreenController.download(.) : Report "
					+ file.getName() + " is downloded successfully at "
					+ file.getPath());
			PopupWindow.popup.createInformationalPopup("Success!",
					"Report " + file.getName()
							+ " created successfully, \n Please find it at \n"
							+ file.getAbsolutePath());
		}
	}

	@FXML
	public void email(ActionEvent event) {
		Job job = validateReportInput(Constants.INSTANT_REPORT_EMAIL);
		File file = null;
		if (job != null) {
			file = ReportManager.getInstantReportGenerator().generateReportNow(
					job);
		}
		if (file != null) {
			Set<User> set = UserManager.getUserManager().getUserList();
			List<String> emailIDList = UserUtil.userEmailIdList(set);
			Mail.sendMail(emailIDList, file);
			Logger.logMessage("DashboardScreenController.emai(.) : Report "
					+ file.getName()
					+ " is generated successfully and sent to following mail id's "
					+ emailIDList.toString());
			PopupWindow.popup
					.createInformationalPopup(
							"Success!",
							"Report "
									+ file.getName()
									+ " created successfully,and sent to following mail id's \n"
									+ emailIDList.toString());
		}
	}

	@FXML
	public void onItemSelection(ActionEvent event) {
		String itemSelected = purchaseTypeComboBox.getSelectionModel()
				.getSelectedItem();
		if (itemSelected.equals(Constants.BULK_PURCHASE_TYPE)) {
			disableDailyPurchaseControls();
			enableBulkPurchaseControls();
			intializeContibutorList();
		} else if (itemSelected.equals(Constants.DAILY_PURCHASE_TYPE)) {
			disableBulkPurchaseControls();
			enableDailsPurchaseControls();
		} else {
			disableBulkPurchaseControls();
			disableDailyPurchaseControls();
		}
	}

	@FXML
	public void onCheckBoxSelected(MouseEvent event) {
		if (othersCheckBox.isSelected()) {
			othersDetailCombo.setDisable(false);
		} else {
			othersDetailCombo.setDisable(true);
		}
	}

	@FXML
	public void onOperationSelection(ActionEvent event) {
		String userSelected = userCombo.getSelectionModel().getSelectedItem();
		User user = UserManager.getUserManager().getUser(
				usernameLabel.getText());
		Button button = (Button) event.getSource();
		if (!user.getUserName().equals(userSelected)) {
			if (button != viewUserBtn) {
				PopupWindow.popup.createErrorPopup("Error!",
						"You are not authorized person for this operations");
				manageMemberStackPane.getChildren().clear();
				return;
			}
		}
		if (userSelected != null) {
			FXMLLoader loader = null;
			if (button == viewUserBtn) {
				loader = loadManagerUserProfileScreen(ViewProfileScreen
						.getViewProfileScreen().getFXMLResource());
				ControllerUtil.controllerUtil.setController(
						ScreenConstant.VIEW_PROFILE_SCREEN,
						loader.<ViewProfileController> getController());
				setUserInfo(userSelected, loader);

			} else if (button == editUserBtn) {
				loader = loadManagerUserProfileScreen(EditProfileScreen
						.getEditProfileScreen().getFXMLResource());
				ControllerUtil.controllerUtil.setController(
						ScreenConstant.EDIT_PROFILE_SCREEN,
						loader.<EditProfileController> getController());
				EditProfileController controller = loader
						.<EditProfileController> getController();
				controller.disableUsernameTextfield();
				editUserInfo(userSelected, loader);

			} else if (button == deleteUserBtn) {

				UserManager manager = UserManager.getUserManager();
				manager.removeUser(manager.getUser(userSelected));
			} else if (button == resetPasswordBtn) {
				loader = loadManagerUserProfileScreen(ResetPasswordScreen
						.getResetPasswordScreen().getFXMLResource());
				ControllerUtil.controllerUtil.setController(
						ScreenConstant.RESET_PASSWORD_SCREEN,
						loader.<ResetPasswordController> getController());
				ResetPasswordController controller = loader
						.<ResetPasswordController> getController();
				controller.getUsernameLabel().setText(userSelected);
			} else if (button == activateUserBtn) {
				UserUpdateRequest request = new UserUpdateRequest(userSelected,
						(byte) 1, null);
				Job job = new Job(Constants.ACTIVATE_USER, request);
				int ret = JobManager.getInstantJobHandler().processJob(job);
				if (ret == Constants.JOB_PROCESSED_SUCCESSFULLY) {
					PopupWindow.popup.createInformationalPopup("Success!",
							"User got Activated");
				}
			} else if (button == deactivateUserBtn) {
				UserUpdateRequest request = new UserUpdateRequest(userSelected,
						(byte) 0, null);
				Job job = new Job(Constants.DEACTIVATE_USER, request);
				int ret = JobManager.getInstantJobHandler().processJob(job);
				if (ret == Constants.JOB_PROCESSED_SUCCESSFULLY) {
					PopupWindow.popup.createInformationalPopup("Success!",
							"User got Deactivated");
				}
			}
		} else {
			PopupWindow.popup.createErrorPopup("Error!",
					"Please Select User first");
		}
	}

	@FXML
	public void creditorPieChart(ActionEvent event) {
		
		pieChart.setData(null);
		userLabelText.setVisible(false);
		amountLabelText.setVisible(false);
		username.setText("");
		amount.setText("");
		
		int erroCode = DatabaseUtil.checkDatabaseConnection();
		Logger.logMessage("DashboardScreenController.creditorPieChart(.) : database connection is alive ? "
				+ erroCode);
		if (erroCode == Constants.DATABASE_CONNECTION_SUCCESS) {
			Job job = new Job(null, null);
			List<AggregateShare> list = null;
			try {
				list = Service.getService().getShareReport(job);
				pieChart.setData(getChartData(list, CREDITOR));
				pieChart.setLegendSide(Side.LEFT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (final PieChart.Data data : pieChart.getData()) {
				data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
						new EventHandler<MouseEvent>() {

							public void handle(MouseEvent e) {
								String name = data.getName();
								userLabelText.setVisible(true);
								amountLabelText.setVisible(true);
								username.setText(name);
								amount.setText("" + data.getPieValue());
							}
						});
			}
		} else {
			PopupWindow.popup.createErrorPopup("Error!",
					"Database connection error, Please try again");
		}
	}

	@FXML
	public void debtorPieChart(ActionEvent event) {
		
		pieChart.setData(null);
		userLabelText.setVisible(false);
		amountLabelText.setVisible(false);
		username.setText("");
		amount.setText("");
		
		int erroCode = DatabaseUtil.checkDatabaseConnection();
		Logger.logMessage("DashboardScreenController.creditorPieChart(.) : database connection is alive ? "
				+ erroCode);
		if (erroCode == Constants.DATABASE_CONNECTION_SUCCESS) {
			Job job = new Job(null, null);
			List<AggregateShare> list = null;
			try {
				list = Service.getService().getShareReport(job);
				pieChart.setData(getChartData(list, DEBTOR));
				pieChart.setLegendSide(Side.LEFT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (final PieChart.Data data : pieChart.getData()) {
				data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
						new EventHandler<MouseEvent>() {

							public void handle(MouseEvent e) {
								String name = data.getName();
								userLabelText.setVisible(true);
								amountLabelText.setVisible(true);
								username.setText(name);
								amount.setText("" + data.getPieValue());
							}
						});
			}
		} else {
			PopupWindow.popup.createErrorPopup("Error!",
					"Database connection error, Please try again");
		}
	}

	// ******************Validation FUNCTIONS*******************

	private boolean validatePurchaseInput() {
		boolean isValid = true;
		String errorMessage = "";

		String itemSelected = purchaseTypeComboBox.getSelectionModel()
				.getSelectedItem();
		if (itemSelected != null) {
			if (dateDatePicker.getValue() == null
					|| !DateUtil.validateDate(DateUtil.getAsDate(dateDatePicker
							.getValue()))) {
				errorMessage += "Selected Date is not valid - "
						+ dateDatePicker.getValue()
						+ ", Please Select proper date!\n";
			}
			if (itemSelected.equals(Constants.DAILY_PURCHASE_TYPE)) {
				if (!milkCurdCheckBox.isSelected()
						&& !kitchenItemsCheckBox.isSelected()
						&& !vegetablesOrDalCheckBox.isSelected()
						&& !othersCheckBox.isSelected()) {
					errorMessage += "None of the items selected\n";
				}
			} else if (itemSelected.equals(Constants.BULK_PURCHASE_TYPE)) {
				if (getContributorLists().size() == 0) {
					errorMessage += "Contributor list is empty. Select contributors!\n";
				}
				if (individualShareTxt.getText() == null
						|| individualShareTxt.getText().length() == 0) {
					errorMessage += "Individual share list is empty. Provide individual share!\n";
				}
				if (getContributorLists().size() != getContributionList()
						.size()) {

					errorMessage += "Error in individual amount, please provide amount in comma separated\n Ex: 100, 200, 300";

				}
				if (!validateIndividualAmountList(getContributionList())) {
					errorMessage += "Mismatch in individual share "
							+ individualShareTxt.getText()
							+ " and total amount "
							+ totalAmountText.getText()
							+ "\n"
							+ " Please check list of contributor and individual share amount are matching or not.";
				}
			}
			String totalAmount = totalAmountText.getText();
			if (totalAmount != null || totalAmount.length() == 0) {
				try {
					Double.parseDouble(totalAmount);
					if (totalAmount.length() > 8
							&& itemSelected
									.equals(Constants.DAILY_PURCHASE_TYPE)) {
						errorMessage += "Total amount is not valid totalAmountText "
								+ totalAmount + "\n";
					}
				} catch (Exception exc) {
					errorMessage += "Total amount is not valid totalAmountText "
							+ totalAmount + "\n";
				}
			}
		} else {
			errorMessage += "Purchase type not selected, please select\n";
		}

		if (errorMessage.length() == 0) {
			isValid = true;
		} else {
			// Show the error message
			PopupWindow.popup.createErrorPopup("Errors in expense details ",
					errorMessage);
			isValid = false;
		}
		Logger.logMessage("DashboardScreenController.validatePurchaseInput() : Errors in purchase detail --> "
				+ errorMessage);
		return isValid;
	}

	private boolean validateIndividualAmountList(List<String> list) {
		boolean retVal = false;
		try {
			double amount = 0.0;
			for (String val : list) {
				amount += Double.parseDouble(val);
			}
			if (amount == Double.parseDouble(totalAmountText.getText())) {
				retVal = true;
			}
		} catch (Exception exc) {
			retVal = false;
			Logger.logMessage("DashboardScreenController.validateIndividualAmountList(.) : Errors in validateIndividualAmountList --> "
					+ exc.getMessage());
		}

		return retVal;
	}

	private Job validateReportInput(String reportOption) {
		Job job = null;

		int selectedMonthIndex = 0;
		String reportTypeSelected = null;
		String monthSelected = null;
		String yearSelected = null;
		String downloadOption = null;
		String emailOption = null;
		String fileOption = null;
		String errorMessage = "";

		Calendar now = Calendar.getInstance();

		reportTypeSelected = reportTypeCombo.getSelectionModel()
				.getSelectedItem();
		if (reportTypeSelected != null) {

			monthSelected = monthCombo.getSelectionModel().getSelectedItem();
			yearSelected = yearCombo.getSelectionModel().getSelectedItem();
			selectedMonthIndex = monthCombo.getSelectionModel()
					.getSelectedIndex();
			if (monthSelected == null || yearSelected == null) {
				errorMessage += "Month/Year not selected, please select month and year"
						+ " \n";
			} else {

				if (now.get(Calendar.YEAR) >= Integer.parseInt(yearSelected)) {
					if (now.get(Calendar.YEAR) == Integer
							.parseInt(yearSelected)
							&& now.get(Calendar.MONTH) > (selectedMonthIndex - 1)) {

					} else {
						errorMessage += "Wrong month selected, select month before now "
								+ " \n";
					}
				} else {
					errorMessage += "Wrong year selected, select current or older year "
							+ " \n";
				}

				if (!DateUtil.validateDate(DateUtil.getAsDate(to.getValue()),
						DateUtil.getAsDate(from.getValue()))) {
					errorMessage += "Selected date is not valid, Please select valid date within one month before now.";
				}
			}
			downloadOption = downloadCombo.getSelectionModel()
					.getSelectedItem();
			emailOption = emailCombo.getSelectionModel().getSelectedItem();

			if (reportOption.equals(Constants.INSTANT_REPORT_DOWNLOAD)) {
				if (downloadOption == null) {
					errorMessage += "File format for download not selected, please select"
							+ " \n";
				} else {

					fileOption = downloadOption;
				}

			} else if (reportOption.equals(Constants.INSTANT_REPORT_EMAIL)) {

				if (emailOption == null) {
					errorMessage += "File format for email not selected, please select"
							+ " \n";
				} else {
					fileOption = emailOption;
				}
			}
		} else {
			errorMessage += "Report type not selected, please select" + " \n";
		}

		if (errorMessage.length() == 0) {
			String fromDate = from.getValue().format(
					DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String toDate = to.getValue().format(
					DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			ReportRequest request = new ReportRequest(reportTypeSelected,
					monthSelected, yearSelected, fromDate, toDate, fileOption);
			Logger.logMessage("DashboardScreenController.validateReportInput(.) : ReportRequest generated without error is "
					+ request + " and report option is " + reportOption);
			job = new Job(reportOption, request);
		} else {
			PopupWindow.popup.createErrorPopup("Errors in Report request ",
					errorMessage);
		}
		Logger.logMessage("DashboardScreenController.validateReportInput(.) : Errors in report request detail --> "
				+ errorMessage);
		return job;
	}

	// ******************UTILITY FUNCTIONS*******************/

	private void initializePurchaseType() {
		String[] elements = { (String) Constants.DAILY_PURCHASE_TYPE,
				(String) Constants.BULK_PURCHASE_TYPE,
				Constants.HOUSE_RENT_TYPE };
		purchaseTypeComboBox.getItems().setAll(elements);

	}

	private void intializeReportFormat() {
		String[] elements = { (String) Constants.PDF_FILE_REPORT,
				(String) Constants.DOCX_FILE_REPORT };
		downloadCombo.getItems().setAll(elements);
		emailCombo.getItems().setAll(elements);
	}

	private void initailzeReportType() {
		String[] elements = { Constants.PURCHASE_REPORT, Constants.SHARE_REPORT };
		reportTypeCombo.getItems().setAll(elements);
	}

	public void onContributionSelection() {
		Logger.logMessage("DashboardScreenController.onContributionSelection() : Event occured"
				+ contributorListCombo.getCheckModel().getCheckedItems());
	}

	public void onOthersSelection(Change<? extends String> c) {
		Logger.logMessage("DashboardScreenController.onOthersSelection() : Event occured"
				+ othersDetailCombo.getCheckModel().getCheckedItems());
		
		List list  = c.getList();
		if (list.contains("Others")) {
			otherDetailText.setDisable(false);
		} else {
			otherDetailText.setDisable(true);
		}
	}

	private void intializeContibutorList() {

		List<com.expensetracker.model.User> modelUserList = null;
		Logger.logMessage("DashboardScreenController.intializeContibutorList() : Populating contributor combo..");
		try {

			List<User> list = new ArrayList<User>(UserManager.getUserManager()
					.getUserList());
			if (list == null) {
				modelUserList = Service.getService().getUserList();
				list = ConvertUtil.modelToPojo(modelUserList);
			}

			if (list != null) {
				String elements[] = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					elements[i] = list.get(i).getUserName();
				}
				contributorListCombo.getItems().setAll(elements);
			} else {
				Logger.logMessage("DashboardScreenController.intializeContibutorList() : No data received from database for populating contributor combo");
			}

		} catch (Exception e) {
			Logger.logMessage("DashboardScreenController.intializeContibutorList() : Error in getting list of users from database for populating contributor combo "
					+ e);
		}
		/*
		 * String[] elements = { "BHARAT", "AMRISH", "PRATAP"};
		 * contributorListCombo.getItems().setAll(elements);
		 */
	}

	private void intializeMemberList() {
		// database and store in local cache.

		List<com.expensetracker.model.User> modelUserList = null;
		Logger.logMessage("DashboardScreenController.intializeMemberList() : Populating user combo..");
		try {

			List<User> list = new ArrayList<User>(UserManager.getUserManager()
					.getUserList());
			if (list == null) {
				modelUserList = Service.getService().getUserList();
				list = ConvertUtil.modelToPojo(modelUserList);
			}

			if (list != null) {
				String elements[] = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					elements[i] = list.get(i).getUserName();
				}
				userCombo.getItems().setAll(elements);
			} else {
				Logger.logMessage("DashboardScreenController.intializeMemberList() : No data received from database for populating user combo");
			}

		} catch (Exception e) {
			Logger.logMessage("DashboardScreenController.intializeMemberList() : Error in getting list of users from database for populating user combo "
					+ e);
		}
		/*
		 * String[] elements = { "BHARAT", "AMRISH", "PRATAP"};
		 * contributorListCombo.getItems().setAll(elements);
		 */
	}

	private void intializeOthersList() {
		// database and store in local cache.
		String[] elements = { "Water Bill", "Electricity Bill", "Cable bill",
				"Internet Bill", "Gas Bill", "Others"};
		othersDetailCombo.getItems().setAll(elements);
	}

	private void intializeMonthAndYear() {
		String[] monthElements = { "JANUARY", "FEBRUARY", "MARCH", "APRIL",
				"MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER",
				"NOVEMBER", "DECEMBER" };
		String[] yearElements = { "2015", "2016", "2017", "2018", "2019",
				"2020" };
		monthCombo.getItems().setAll(monthElements);
		yearCombo.getItems().setAll(yearElements);

	}

	private void enableDailsPurchaseControls() {
		memberTxt.setDisable(false);
		milkCurdCheckBox.setDisable(false);
		kitchenItemsCheckBox.setDisable(false);
		vegetablesOrDalCheckBox.setDisable(false);
		othersCheckBox.setDisable(false);
		memberLabel.setDisable(false);
		checkboxLabel.setDisable(false);
	}

	private void disableBulkPurchaseControls() {
		contributorListCombo.setDisable(true);
		individualShareTxt.setDisable(true);
		contributorListLabel.setDisable(true);
		amountSharedLabel.setDisable(true);
	}

	private void enableBulkPurchaseControls() {
		contributorListCombo.setDisable(false);
		individualShareTxt.setDisable(false);
		amountSharedLabel.setDisable(false);
		contributorListLabel.setDisable(false);
	}

	private void disableDailyPurchaseControls() {
		memberTxt.setDisable(true);
		milkCurdCheckBox.setDisable(true);
		kitchenItemsCheckBox.setDisable(true);
		vegetablesOrDalCheckBox.setDisable(true);
		othersCheckBox.setDisable(true);
		memberLabel.setDisable(true);
		checkboxLabel.setDisable(true);
	}

	private Purchase createPurchaseDetail() {

		String purchaseType = purchaseTypeComboBox.getValue();
		Purchase detail = null;
		if (validatePurchaseInput()) {
			detail = new Purchase();
			detail.setPurchaseType(purchaseType);
			detail.setDate(dateDatePicker.getValue().format(
					DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			detail.setTotalAmount(totalAmountText.getText());
			if (purchaseType.equals(Constants.DAILY_PURCHASE_TYPE)) {
				createDailyItemList();

			} else if (purchaseType.equals(Constants.BULK_PURCHASE_TYPE)) {
				detail.setContributorList(getContributorLists());
				detail.setContributionList(getContributionList());
			}
			detail.setMember(memberTxt.getText());
			detail.setItemsList(itemsList);
		}
		return detail;
	}

	private List<String> getContributionList() {
		List<String> list = new ArrayList<String>();

		String[] individualShare = individualShareTxt.getText().split(",");
		for (String share : individualShare) {
			list.add(share.trim());
		}
		return list;
	}

	private List<String> getContributorLists() {
		List<String> contributorList = new ArrayList<String>();
		ObservableList<String> list = contributorListCombo.getCheckModel()
				.getCheckedItems();
		for (String contributor : list) {
			contributorList.add(contributor.trim());
		}
		return contributorList;
	}

	private void createDailyItemList() {
		itemsList = "";
		if (milkCurdCheckBox.isSelected()) {
			itemsList += "Milk/Curd, ";
		}
		if (kitchenItemsCheckBox.isSelected()) {
			itemsList += "KitchenItems, ";
		}
		if (vegetablesOrDalCheckBox.isSelected()) {
			itemsList += "Vegetables/Dal, ";
		}
		if (othersCheckBox.isSelected()) {
			itemsList += createOthersList() + ", ";
		}
		itemsList = itemsList.substring(0, (itemsList.length() - 2));
	}

	private String createOthersList() {
		String otherItemList = "";
		ObservableList<String> list = othersDetailCombo.getCheckModel()
				.getCheckedItems();
		for (String item : list) {
			if (item.equals("Others")) {
				item = otherDetailText.getText();
			}
			
			otherItemList += item + ", ";
		}

		otherItemList = otherItemList
				.substring(0, (otherItemList.length() - 2));
		return otherItemList;
	}

	public void setLabel(String usernamelabel) {
		usernameLabel.setText(usernamelabel);
	}

	public void setMember(String memberName) {
		memberTxt.setText(memberName);
	}

	private void setUserInfo(String userSelected, FXMLLoader loader) {

		User user = UserManager.getUserManager().getUser(userSelected);

		ViewProfileController controller = loader
				.<ViewProfileController> getController();
		controller.setUsernameLabel(user.getUserName());
		controller.setFirstnameLabel(user.getFirstname());
		controller.setLastnameLabel(user.getLastName());
		controller.setEmailIdLabel(user.getEmailId());
		controller.setContactLabel(user.getMobileNumber());
		byte[] image = user.getImage();
		if (image != null) {
			ByteArrayInputStream in = new ByteArrayInputStream(image);
			try {
				BufferedImage read = ImageUtil.getScaledImage(ImageIO.read(in),
						150, 120);
				controller.setUserImageView(SwingFXUtils.toFXImage(read, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void editUserInfo(String userSelected, FXMLLoader loader) {

		User user = UserManager.getUserManager().getUser(userSelected);

		EditProfileController controller = loader
				.<EditProfileController> getController();
		controller.setUsernameTxt(user.getUserName());
		controller.setFirstNameText(user.getFirstname());
		controller.setLastnameTxt(user.getLastName());
		controller.setEmailIdTxt(user.getEmailId());
		controller.setContactTxt(user.getMobileNumber());
		byte[] image = user.getImage();
		if (image != null) {
			ByteArrayInputStream in = new ByteArrayInputStream(image);
			try {
				BufferedImage read = ImageUtil.getScaledImage(ImageIO.read(in),
						150, 120);
				controller.setUserImageView(SwingFXUtils.toFXImage(read, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public FXMLLoader loadManagerUserProfileScreen(URL url) {
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader();
			loader.setLocation(url);
			StackPane layout = (StackPane) loader.load();

			manageMemberStackPane.getChildren().setAll(layout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loader;
	}

	private ObservableList<Data> getChartData(List<AggregateShare> list,
			String type) {
		// TODO Auto-generated method stub
		ObservableList<PieChart.Data> pieChartData = FXCollections
				.observableArrayList();
		PieChart.Data data = null;
		for (AggregateShare share : list) {
			if (type.equals(CREDITOR) && share.getCurrentShare() >= 0) {
				data = new PieChart.Data(share.getUserName(),
						Math.ceil(Math.abs(share.getCurrentShare())));
				pieChartData.add(data);
			} else if (type.equals(DEBTOR) && share.getCurrentShare() < 0) {
				data = new PieChart.Data(share.getUserName(),
						Math.ceil(Math.abs(share.getCurrentShare())));
				pieChartData.add(data);
			}
		}

		return pieChartData;
	}

	private void clear() {
		dateDatePicker.setValue(null);
		milkCurdCheckBox.setSelected(false);
		kitchenItemsCheckBox.setSelected(false);
		vegetablesOrDalCheckBox.setSelected(false);
		othersCheckBox.setSelected(false);
		othersDetailCombo.getCheckModel().clearChecks();
		contributorListCombo.getCheckModel().clearChecks();
		othersDetailCombo.setDisable(true);
		individualShareTxt.clear();
		totalAmountText.clear();
	}
}
