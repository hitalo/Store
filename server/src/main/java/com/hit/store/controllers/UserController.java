package com.hit.store.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hit.store.models.People;
import com.hit.store.models.User;
import com.hit.store.models.dto.UserPermission;
import com.hit.store.models.dto.UserRole;
import com.hit.store.repositories.PeopleRepository;
import com.hit.store.repositories.UserRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PeopleRepository peopleRepository;
	
	
	@GetMapping("/getAll")
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	
	@GetMapping("/getById")
	public User getById(@RequestBody User user) {
		final Long id = user.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<User> result = userRepository.findById(id);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@GetMapping("/getByEmail")
	public User getByEmail(@RequestBody User user) {
		final String email = user.getPeople().getEmail();
		if(email == null) throw new IllegalArgumentException("email can't be null");
		final Optional<People> resultPeople = peopleRepository.findByEmail(email);
		if(!resultPeople.isPresent()) return null;
		final Optional<User> resultUser = userRepository.findByPeople(resultPeople.get());
		if(!resultUser.isPresent()) return null;
		return resultUser.get();
	}
	
	
	@PostMapping("/addWithNewPeople")
	public Long addUserNewPeople(@RequestBody User user) {
		if(user.getLogin() == null || user.getLogin().trim().equals("") || !Validator.isPasswordValid(user.getPassword()))
			throw new IllegalArgumentException("login or email invalid");
		peopleRepository.save(user.getPeople());
		return userRepository.save(user).getId();
	}
	
	
	@PostMapping("/addWithExistingPeople")
	public Long addUserExistingPeople(@RequestBody User user) {
		final Long peopleId = user.getPeople().getId();
		if(user.getLogin() == null || user.getLogin().trim().equals("") || !Validator.isPasswordValid(user.getPassword()))
			throw new IllegalArgumentException("login or email invalid");
		
		final Optional<People> result = peopleRepository.findById(peopleId);
		if(peopleId == null || !result.isPresent()) 
			throw new IllegalArgumentException("Personal data missing");
		
		final People people = result.get();
		if(userRepository.findByPeople(people).isPresent()) throw new IllegalArgumentException("User already exists");
		
		user.setPeople(people);
		return userRepository.save(user).getId();
	}
	
	
	@PutMapping("/update")
	public Long updateUser(@RequestBody User user) {
		final Long peopleId = user.getPeople().getId();
		final Long id = user.getId();
		
		if(id == null) throw new IllegalArgumentException("Can't find person with id: " + id);
		if(user.getLogin() == null || user.getLogin().trim().equals("") || !Validator.isPasswordValid(user.getPassword()))
			throw new IllegalArgumentException("login or email invalid");
		
		final Optional<People> result = peopleRepository.findById(peopleId);
		if(peopleId == null || !result.isPresent()) 
			throw new IllegalArgumentException("Personal data missing");
		
		return userRepository.save(user).getId();
	}
	
	
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public Object deleteUser(@RequestBody User user) {
		final Long id = user.getId();
		if(id == null) return null;
		final Optional<User> result = userRepository.findById(id);
		if(!result.isPresent()) return null;
		userRepository.deleteById(id);
		return new HashMap(){{put("message", "deleted");}};
	}
	
	
	@PutMapping("/updateUserPermissions")
	public void updateUserPermissions(@RequestBody UserPermission userPermissions) {
		final Long id = userPermissions.getUserId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<User> resultUser = userRepository.findById(id);
		if(!resultUser.isPresent()) throw new IllegalArgumentException("Not found user with id: " + id);
		final User user = resultUser.get();
		user.setPermissions(userPermissions.getPermissions());
		userRepository.save(user);
	}
	
	
	@PutMapping("/updateUserRoles")
	public void updateUserRoles(@RequestBody UserRole userRole) {
		final Long id = userRole.getUserId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<User> resultUser = userRepository.findById(id);
		if(!resultUser.isPresent()) throw new IllegalArgumentException("Not found user with id: " + id);
		final User user = resultUser.get();
		user.setRoles(userRole.getRoles());
		userRepository.save(user);
	}
}
