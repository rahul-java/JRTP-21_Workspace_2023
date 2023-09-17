package com.app.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.User;
import com.app.repo.UserRepository;

@RestController
public class UserRestController {

	private UserRepository userRepository;
	
	public UserRestController(UserRepository userRepository)
	{
		this.userRepository=userRepository;
	}

	@PostMapping(value = "/user")
	public User addUser(@RequestBody User user)
	{
		userRepository.save(user);
		return userRepository.findById(user.getId());
	}
	
	@PutMapping(value= "/user/{id}/{name}" , consumes="application/json")
	public User update(@PathVariable("id") final String id,@PathVariable("name") final String name)
	{
		userRepository.update(new User(id,name,1000l));
		return userRepository.findById(id);
	}
	
	@DeleteMapping("/user/{id}")
	public Map<String, User> delete(@PathVariable("id") final String id)
	{
		userRepository.delete(id);
		return all();
	}
	
	@GetMapping("/users")
	public Map<String, User> all(){
		Map<String, User> findAll = userRepository.findAll();
		return findAll;
	}
	
	
	
}
