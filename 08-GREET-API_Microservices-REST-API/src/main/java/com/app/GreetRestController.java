package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetRestController {

	@Autowired
	private Environment environment;
	
	@GetMapping("/greet")
	public String getGreetMsg()
	{
		String portNo = environment.getProperty("server.port");
		
		String msg="Good Evening... (From Server ::: "+portNo+" )";
		return msg;
	}
}
