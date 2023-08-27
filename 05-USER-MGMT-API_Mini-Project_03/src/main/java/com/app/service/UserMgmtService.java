package com.app.service;

import java.util.List;

import com.app.bindings.ActivateAccount;
import com.app.bindings.Login;
import com.app.bindings.User;

public interface UserMgmtService {

	public boolean saveUser(User user);
	public boolean activateUserAcct(ActivateAccount activateAccount);
	
	public User getUserById(Integer userId);
	public User getUserByEmail(String email);
	public List<User> getAllUsers();
	public boolean deleteUserById(Integer userId);
	public boolean changeAccountStatus(Integer userId,String acctStatus);
	
	public String login(Login login);
	public String forgotPwd(String email);
	
}
