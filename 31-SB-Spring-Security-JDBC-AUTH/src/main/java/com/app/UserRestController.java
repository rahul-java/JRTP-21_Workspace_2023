package com.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@GetMapping("/")
	public String welcome()
	{
		return "<h1>Welcome to Ashok it ::: :)</h1>";
	}
	
	@GetMapping("/admin")
	public String adminProcess()
	{
		return "<h2>Welcome Admin ::: ADMIN_ROLE</h2>";
	}
	
	@GetMapping("/user")
	public String userProcess()
	{
		return "<h3>Welcome user ::: USER_ROLE</h3>";
	}
	
	
}
