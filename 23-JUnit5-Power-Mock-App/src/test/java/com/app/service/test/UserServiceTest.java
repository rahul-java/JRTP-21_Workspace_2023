package com.app.service.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import com.app.dao.UserDao;
import com.app.service.UserService;



public class UserServiceTest {

	@Test
	public void getNameTest() {
		
		// create Mock Obj
		UserDao userDaoMock = PowerMockito.mock(UserDao.class);
		
		//defining mock obj behaviour
		PowerMockito.when(userDaoMock.findNameById(101)).thenReturn("RAJU");
		
		//injecting mock obj into service obj
		UserService userService = new UserService(userDaoMock);
		
		String actualName = userService.getName(101);
		String expectedName="RAJU";
		
		assertEquals(expectedName, actualName);
		
	}
	
	@Test
	public void loginTest()
	{
		UserDao userDaoMock = PowerMockito.mock(UserDao.class);
		PowerMockito.when(userDaoMock.findByEmailAndPwd("admin@gmail.com", "admin")).thenReturn(true);
		
		UserService userService = new UserService(userDaoMock);
		String login = userService.login("admin@gmail.com", "admin");
		String expectedString="SUCCESS"; 
		assertEquals(expectedString, login);
	}
}
