package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.models.AuthenticateRequest;
import com.app.security.MyUserDetailsService;
import com.app.util.JwtUtil;

@RestController
public class AuthenticateRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/authenticate")
	public String authenticateUser(@RequestBody AuthenticateRequest request) throws Exception
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (Exception e) {
			throw new Exception("Invalid Credentials... ::: "+e);
		}
		
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		
		return token;
	}
}
