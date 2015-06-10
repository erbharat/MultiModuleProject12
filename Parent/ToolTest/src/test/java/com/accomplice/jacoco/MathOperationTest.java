package com.accomplice.jacoco;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MathOperationTest {
	
	MathOperation mt = new MathOperation();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		Assert.assertEquals(6, mt.add(3, 3));
		Assert.assertNotSame(6, mt.add(3, 4));
	}
	
	@Test
	public void testMultiply() {
		Assert.assertEquals(9, mt.multiply(3, 3));
		Assert.assertNotSame(12, mt.multiply(3, 4));
	}
}
