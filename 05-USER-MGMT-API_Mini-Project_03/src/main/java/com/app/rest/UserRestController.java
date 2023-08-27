package com.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bindings.ActivateAccount;
import com.app.bindings.Login;
import com.app.bindings.User;
import com.app.service.UserMgmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping("/user")
	public ResponseEntity<String> userReg(@RequestBody User user)
	{
		boolean saveUser = userMgmtService.saveUser(user);
		if(saveUser)
		{
			return new ResponseEntity<>("Registration Successful",HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Registration Failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/activate")
	public ResponseEntity<String> activateAccount(@RequestBody ActivateAccount acc)
	{
		boolean isActivated = userMgmtService.activateUserAcct(acc);
		if(isActivated)
		{
			return new ResponseEntity<>("Account Activated",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Invalid Temporary Pwd",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers()
	{
		List<User> allUsers = userMgmtService.getAllUsers();
		return new ResponseEntity<>(allUsers,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId)
	{
		User userById = userMgmtService.getUserById(userId);
		return new ResponseEntity<>(userById,HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer userId)
	{
		
		boolean isDeleted = userMgmtService.deleteUserById(userId);
		if(isDeleted)
		{
			return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/status/{userId}/{status}")
	public ResponseEntity<String> changeStatus(@PathVariable Integer userId,@PathVariable String status)
	{
		boolean isChangeStatus = userMgmtService.changeAccountStatus(userId, status);
		if(isChangeStatus)
		{
			return new ResponseEntity<>("Status Changed Successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Failed to Change",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//check it once by using getmapping
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login)
	{
		String status = userMgmtService.login(login);
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	
	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email)
	{
		String status = userMgmtService.forgotPwd(email);
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	
}
