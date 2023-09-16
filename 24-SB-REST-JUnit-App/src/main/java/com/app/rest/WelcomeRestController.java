package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.WelcomeService;

@RestController
public class WelcomeRestController {

	@Autowired
	private WelcomeService welcomeService;
	
	@GetMapping("/welcome")
	private ResponseEntity<String> welcome()
	{
		String welcomeMsg = welcomeService.getWelcomeMsg();
		return new ResponseEntity<String>(welcomeMsg,HttpStatus.OK);
	}
	
	@GetMapping("/greet")
	private ResponseEntity<String> greet()
	{
		String welcomeMsg = welcomeService.getGreetMsg();
		return new ResponseEntity<String>(welcomeMsg,HttpStatus.OK);
	}
}
