package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestController {

	Logger logger=LoggerFactory.getLogger(GreetingRestController.class);
	
	@GetMapping("/greet")
	public String greetingMsg()
	{
		logger.info("greeting() Msg is started.......");
		String msg="Good Morning....";
		logger.info("greeting() Msg is completed.......");
		return msg;
	}
}
