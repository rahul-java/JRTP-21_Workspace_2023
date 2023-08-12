package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRestController {

	Logger logger=LoggerFactory.getLogger(DemoRestController.class);
	
	@GetMapping("/welcome")
	public String getWelcomeMsg()
	{
		logger.info(" getWelcomeMsg() execution started...");
		String msgString= "Hello , Welcome to JRTP";
		
		try {
			int i=10/0;
		} catch (Exception e) {
			logger.error("Exception occured : "+e.getMessage());
		}
		
		logger.info(" getWelcomeMsg() execution ended...");
		return msgString;
	}
}
