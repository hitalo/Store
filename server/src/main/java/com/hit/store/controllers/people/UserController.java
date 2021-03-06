package com.hit.store.controllers.people;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hit.store.models.dto.UserPermission;
import com.hit.store.models.dto.UserRole;
import com.hit.store.models.people.People;
import com.hit.store.models.people.User;
import com.hit.store.repositories.people.PeopleRepository;
import com.hit.store.repositories.people.UserRepository;
//import com.hit.store.services.MyUserDetailsService;
import com.hit.store.utils.Constants;
import com.hit.store.utils.Validator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		final String pass = user.getPassword();
		if(user.getLogin() == null || user.getLogin().trim().equals("") || !Validator.isPasswordValid(pass))
			throw new IllegalArgumentException("login or password invalid");
		String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt(10));
		user.setPassword(hashedPassword);
		peopleRepository.save(user.getPeople());
		return userRepository.save(user).getId();
	}
	
	
	@PostMapping("/addWithExistingPeople")
	public Long addUserExistingPeople(@RequestBody User user) {
		final Long peopleId = user.getPeople().getId();
		final String pass = user.getPassword();
		if(user.getLogin() == null || user.getLogin().trim().equals("") || !Validator.isPasswordValid(pass))
			throw new IllegalArgumentException("login or password invalid");
		
		final Optional<People> result = peopleRepository.findById(peopleId);
		if(peopleId == null || !result.isPresent()) 
			throw new IllegalArgumentException("Personal data missing");
		
		final People people = result.get();
		if(userRepository.findByPeople(people).isPresent()) throw new IllegalArgumentException("User already exists");
		
		String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt(10));
		user.setPassword(hashedPassword);
		
		user.setPeople(people);
		return userRepository.save(user).getId();
	}
	
	
	@PutMapping("/update")
	public Long updateUser(@RequestBody User user) {
		final Long peopleId = user.getPeople().getId();
		final Long id = user.getId();
		final String pass = user.getPassword();
		
		if(id == null) throw new IllegalArgumentException("Can't find person with id: " + id);
		if(user.getLogin() == null || user.getLogin().trim().equals("") || !Validator.isPasswordValid(pass))
			throw new IllegalArgumentException("login or password invalid");
		
		final Optional<People> result = peopleRepository.findById(peopleId);
		if(peopleId == null || !result.isPresent()) 
			throw new IllegalArgumentException("Personal data missing");
		
		String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt(10));
		user.setPassword(hashedPassword);
		
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
	
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		final String login = user.getLogin();
		if(login == null || login.trim().equals("") || !Validator.isPasswordValid(user.getPassword()))
			throw new IllegalArgumentException("login or password invalid");
		final Optional<User> result = userRepository.findByLogin(login);
		if(!result.isPresent()) throw new IllegalArgumentException("login invalid");
		final User resultUser = result.get();
		if(!BCrypt.checkpw(user.getPassword(), resultUser.getPassword())) throw new IllegalArgumentException("password invalid");
		return generateJWTToken(resultUser);
	}
	
	
	private String generateJWTToken(User user) {
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
				.claim("userId", user.getId())
				.claim("login", user.getLogin())
				.claim("name", user.getPeople().getName())
				.compact();
		return token;
	}
}
