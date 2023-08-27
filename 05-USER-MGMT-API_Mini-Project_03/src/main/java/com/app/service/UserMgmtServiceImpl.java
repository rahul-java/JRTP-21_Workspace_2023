package com.app.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.app.bindings.ActivateAccount;
import com.app.bindings.Login;
import com.app.bindings.User;
import com.app.entity.UserMaster;
import com.app.repo.UserMasterRepo;
import com.app.utils.EmailUtils;


@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserMasterRepo userMasterRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	private  Random random = new Random();
	
	private Logger logger=LoggerFactory.getLogger(UserMgmtServiceImpl.class);
	
	@Override
	public boolean saveUser(User user) {
		
		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);
		
		entity.setPassword(generateRandomPwd());
		entity.setAcctStatus("In-Active");
		
		UserMaster save = userMasterRepo.save(entity);
		
		String subject="Your Registration Successfull";

		String fileName="REG-EMAIL-BODY.txt";
		
		String body=readRegEmailBody(entity.getFullName(), entity.getPassword(),fileName);
		
		emailUtils.sendEmail(user.getEmail(), subject, body);
		
		return save.getUserId()!=null;
	}

	@Override
	public boolean activateUserAcct(ActivateAccount activateAccount) {

		UserMaster entity = new UserMaster();
		
		entity.setEmail(activateAccount.getEmail());
		entity.setPassword(activateAccount.getTempPwd());
		
		Example<UserMaster> of = Example.of(entity);
		List<UserMaster> findAll = userMasterRepo.findAll(of);
		
		if(findAll.isEmpty())
		{
			return false;
		}
		else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activateAccount.getNewPwd());
			userMaster.setAcctStatus("Active");
			userMasterRepo.save(userMaster);
			return true;
		}
	}

	@Override
	public User getUserById(Integer userId) {

		Optional<UserMaster> findById = userMasterRepo.findById(userId);
		if(findById.isPresent())
		{
			UserMaster userMaster = findById.get();
			User user = new User();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> findAll = userMasterRepo.findAll();
		List<User> users=new ArrayList<>();
		for(UserMaster entity:findAll)
		{
			User user = new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public boolean deleteUserById(Integer userId) {

		try {
			userMasterRepo.deleteById(userId);
			return true;
		} catch (Exception e) {

			//e.printStackTrace();
			logger.error("Exception Occured : ", e);
		}
		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String acctStatus) {

		Optional<UserMaster> findById = userMasterRepo.findById(userId);
		if(findById.isPresent())
		{
			UserMaster userMaster = findById.get();
			userMaster.setAcctStatus(acctStatus);
			userMasterRepo.save(userMaster);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {

		UserMaster entity = userMasterRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		
		if(entity==null)
		{
			return "Invalid Credentials...";
		}
		else {
			if(entity.getAcctStatus().equals("Active"))
			{
				return "SUCCESS";
			}
			else {
				return "Account Not Activated";
			}
		}
		/*
		UserMaster entity=new UserMaster();
		entity.setEmail(login.getEmail());
		entity.setPassword(login.getPassword());
		
		Example<UserMaster> of = Example.of(entity);
		List<UserMaster> findAll = userMasterRepo.findAll(of);
		
		
		if(findAll.isEmpty())
		{
			return "Invalid Credentials...";
		}
		else {
			UserMaster userMaster = findAll.get(0);
			if(userMaster.getAcctStatus().equals("Active"))
			{
				return "SUCCESS";
			}
			else {
				return "Account Not Activated";
			}
		}
		*/
	}

	@Override
	public String forgotPwd(String email) {

		UserMaster entity = userMasterRepo.findByEmail(email);
		if(entity==null)
		{
			return "Invalid Email";
		}
		else {
			//TODO: Send pwd to email
			String subject="Forgot Password";

			String fileName="RECOVER-PWD-BODY.txt";
			
			String body=readRegEmailBody(entity.getFullName(), entity.getPassword(),fileName);
			
			boolean sendEmail = emailUtils.sendEmail(email, subject, body);
			
			if (sendEmail) {
				return "Password Sent to Your Registered Email.";
			}
		}
		return null;
	}
	
	private String generateRandomPwd()
	{
		    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		    String numbers = "0123456789";

		    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
		    StringBuilder sb = new StringBuilder();
		   
		    int length = 6;
		    for(int i = 0; i < length; i++) {
		      int index = this.random.nextInt(alphaNumeric.length());
		      char randomChar = alphaNumeric.charAt(index);
		      sb.append(randomChar);
		    }

		    return sb.toString();
	}
	
	public String readRegEmailBody(String fullName,String pwd,String fileName)
	{
		String url="";
		String mailBody=null;
		
		//FileReader fr;
		try (
				FileReader fr = new FileReader(fileName);
				BufferedReader br=new BufferedReader(fr);
				
				){
			//fr = new FileReader(fileName);
			//BufferedReader br=new BufferedReader(fr);
			
			StringBuilder builder=new StringBuilder();
			
			
			String line=br.readLine();
			while(line!=null)
			{
				builder.append(line);
				
				 line=br.readLine();
			}
			//br.close();
			
			mailBody = builder.toString();
			mailBody = mailBody.replace("{FullName}", fullName);
			mailBody = mailBody.replace("{TEMP-PWD}", pwd);
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
		} catch (Exception e) {

			//e.printStackTrace();
			logger.error("Exception Occured : ", e);
		} 
		
		
		return mailBody;
	}

}
