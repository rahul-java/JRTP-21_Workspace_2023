package com.app.repo;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.app.binding.User;

@Repository
public class UserRepositoryImpl implements UserRepository{

	private HashOperations<String, String, User> hashOperations;
	
	public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
		
		hashOperations = redisTemplate.opsForHash();
		//redisTemplate.delete("USER");  // this will delete all the user in this HashKey "USER"
		

	}
	
	@Override
	public void save(User user) {

		hashOperations.put("USER", user.getId(), user);
	}

	@Override
	public Map<String, User> findAll() {

		Map<String, User> entries = hashOperations.entries("USER");
		return entries;
	}

	@Override
	public User findById(String id) {

		User user = hashOperations.get("USER", id);
		return user;
	}

	@Override
	public void update(User user) {

		save(user);
	}

	@Override
	public void delete(String id) {

		hashOperations.delete("USER", id);
		
	}

	
}
