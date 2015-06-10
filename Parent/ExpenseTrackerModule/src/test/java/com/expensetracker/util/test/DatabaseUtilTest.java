package com.expensetracker.util.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.util.DatabaseUtil;

public class DatabaseUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDatabaseConnection() {
		Assert.assertEquals(Constants.DATABASE_CONNECTION_SUCCESS, DatabaseUtil.checkDatabaseConnection());
	}

}
