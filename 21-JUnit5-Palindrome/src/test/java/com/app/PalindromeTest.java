package com.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PalindromeTest {

	@ParameterizedTest
	@ValueSource(strings = {"racecar","liril","madam","rahulluhar"})
	public void isPalindromeTest(String str)
	{
		//creating target object
		PalindromeCheck palindromeCheck = new PalindromeCheck();
		
		//calling target class method
		boolean palindrome = palindromeCheck.isPalindrome(str);
		
		//verifying result
		assertTrue(palindrome);
	}
}
