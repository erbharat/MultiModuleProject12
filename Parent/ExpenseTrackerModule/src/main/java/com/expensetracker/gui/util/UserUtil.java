package com.expensetracker.gui.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.expensetracker.gui.pojo.User;

public class UserUtil {
	
	public static List<String> userEmailIdList(Set<User> userList) {
		List<String> userEmaillist = new ArrayList<String>(); 
		Iterator<User> userItr = userList.iterator();
		
		while (userItr.hasNext()) {
			User user = userItr.next();
			userEmaillist.add(user.getEmailId());
		}
		return userEmaillist;
		
	}

}
