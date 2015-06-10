package com.expensetracker.dao.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.expensetracker.dao.UserDao;
import com.expensetracker.model.User;


public class UserDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSaveUserDao() throws Exception {
		UserDao userDao = UserDao.getUserDao();
		User user = new User();
		user.setFirstName("Expence");
		user.setLastName("Tracker");
		user.setUserName("admin");
		user.setEmailId("reportAtexpenseTracker@gamil.com");
		user.setPassword("password");
		user.setStatus(1);
		user.setMobileNo(9980408402L);
		
		userDao.save(user);
		
		User dbUser = userDao.read(user.getUserName());
		Assert.assertNotNull(dbUser);
		
		userDao.delete(dbUser.getUserName());
		
	}

}
