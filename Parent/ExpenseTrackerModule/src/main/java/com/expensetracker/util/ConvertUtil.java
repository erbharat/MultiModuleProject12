package com.expensetracker.util;

import java.util.ArrayList;
import java.util.List;

import com.expensetracker.gui.util.DateUtil;
import com.expensetracker.model.Purchase;
import com.expensetracker.model.User;

public class ConvertUtil {

	public static Purchase convertPurchasePojoToModel(
			com.expensetracker.gui.pojo.Purchase purchase) {

		Purchase purchaseModel = new Purchase();
		purchaseModel.setContributionList(convertListToString(purchase
				.getContributionList()));
		purchaseModel.setContributorList(convertListToString(purchase
				.getContributorList()));
		purchaseModel.setItemList(purchase.getItemsList());
		purchaseModel.setPurchaseDate(purchase.getDate());
		// TODO change date type
		purchaseModel.setUserId(purchase.getMember());
		purchaseModel.setPurchaseType(purchase.getPurchaseType());
		purchaseModel
				.setTotalAmount(Integer.parseInt(purchase.getTotalAmount()));
		return purchaseModel;
	}

	public static String convertListToString(List<String> list) {

		String stringList = "";
		if (list != null && list.size() > 0) {
			for (String item : list) {
				stringList += item + ", ";
			}

			stringList = stringList.substring(0, (stringList.length() - 2));
		}
		return stringList;
	}

	public static User convertUserPojoToModel(
			com.expensetracker.gui.pojo.User guiUser) {

		User user = new User();
		user.setUserName(guiUser.getUserName());
		user.setFirstName(guiUser.getFirstname());
		user.setLastName(guiUser.getLastName());
		user.setEmailId(guiUser.getEmailId());
		user.setMobileNo(Long.parseLong(guiUser.getMobileNumber()));
		user.setPassword(guiUser.getPassword());
		user.setStatus(guiUser.getStatus());
		user.setImage(guiUser.getImage());
		return user;
	}

	public static List<com.expensetracker.gui.pojo.User> modelToPojo(
			List<User> modelUserList) {
		List<com.expensetracker.gui.pojo.User> list = new ArrayList<com.expensetracker.gui.pojo.User>();
		for (User user : modelUserList) {
			com.expensetracker.gui.pojo.User userPojo = new com.expensetracker.gui.pojo.User(
					user.getFirstName(), user.getLastName(),
					user.getUserName(), user.getPassword(), user.getEmailId(),
					String.valueOf(user.getMobileNo()), (byte) user.getStatus());
			userPojo.setImage(user.getImage());
			list.add(userPojo);
		}

		return list;
	}
}
