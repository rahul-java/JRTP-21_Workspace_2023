package com.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestController {

	@GetMapping("/")
	public String welcome()
	{
		return "Welcome to SBI Bank...!!!";
	}
	
	@GetMapping("/transfer")
	public String transfer()
	{
		return "Fund Transfer Initiated...";
	}
	
	@GetMapping("/balance")
	public String chackBalance()
	{
		return "Your Account Balance is :::: $12458.25";
	}
	
	@GetMapping("/about")
	public String aboutUs()
	{
		return "We are People Bank, SBI Bank...!!!";
	}
}
