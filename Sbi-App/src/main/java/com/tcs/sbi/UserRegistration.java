package com.tcs.sbi;

import com.app.security.PasswordSecurityService;

public class UserRegistration {

	public static void main(String[] args) {
		PasswordSecurityService pass=new PasswordSecurityService();
		String encode=pass.encode("Rahul");
		System.out.println(encode);
	}
}
