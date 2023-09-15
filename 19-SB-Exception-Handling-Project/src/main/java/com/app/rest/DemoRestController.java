package com.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRestController {

	@GetMapping("/demo")
	public String getMessage()
	{
		String s=null;
		
		s.length();  // here it will throw NullPinterException
		
		return "Hi";
	}
}
