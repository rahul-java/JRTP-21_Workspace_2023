package com.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

	@Test
	public void stringToIntTest1()
	{
		StringUtils stringUtils = new StringUtils();
		assertThrows(IllegalArgumentException.class, ()->stringUtils.stringToInt(null));
	}
	
	@Test
	public void stringToIntTest2()
	{
		StringUtils stringUtils = new StringUtils();
		assertThrows(IllegalArgumentException.class, ()->stringUtils.stringToInt(" "));
	}
	
	@Test
	public void stringToIntTest3()
	{
		StringUtils stringUtils = new StringUtils();

		Integer stringToInt = stringUtils.stringToInt("121");
		assertEquals(121, stringToInt);
	}
}
