package com.app;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MyRestController {

	@GetMapping("/welcome")
	public String getWelcomeMsg()
	{
		String msg="Wlcome to Fullstack Development...";
		return msg;
	}
	
	@GetMapping("/wish")
	public String getWishMsg()
	{
		String msg="All the best...";
		return msg;
	}
	
}
