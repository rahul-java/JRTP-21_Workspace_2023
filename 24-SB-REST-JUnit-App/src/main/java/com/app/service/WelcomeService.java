package com.app.service;

import org.springframework.stereotype.Service;

@Service
public class WelcomeService {

	public String getWelcomeMsg()
	{
		String msg="Good Morning...";
		return msg;
	}
	
	public String getGreetMsg()
	{
		String msg="Good Evening !!!";
		return msg;
	}
}
