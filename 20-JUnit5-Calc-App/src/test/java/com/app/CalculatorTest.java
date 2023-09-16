package com.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void addTest()
	{
		Calculator calculator = new Calculator();
		int actualResult = calculator.add(5, 6);
		int expectedResult=11;
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void mulTest()
	{
		Calculator calculator = new Calculator();
		int actualResult = calculator.multiply(5, 6);
		int expectedResult=30;
		assertEquals(expectedResult, actualResult);
		
	}
}
