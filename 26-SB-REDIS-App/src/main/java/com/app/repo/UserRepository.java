package com.app.repo;

import java.util.Map;

import com.app.binding.User;

public interface UserRepository {

	void save(User user);
	
	Map<String, User> findAll();
	
	User findById(String id);
	
	void update(User user);
	
	void delete(String id);
}
