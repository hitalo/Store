package com.hit.store.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hit.store.models.People;
import com.hit.store.repositories.PeopleRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/people")
public class PeopleController {

	@Autowired
	private PeopleRepository peopleRepository;
	
	
	@GetMapping("/getAll")
	public List<People> getAllPeople() {
		return peopleRepository.findAll();
	}
	
	
	@GetMapping("/getByEmail")
	public People getPeopleByEMail(@RequestBody People people) {
		final String email = people.getEmail();
		if(!Validator.isEmailValid(email)) throw new IllegalArgumentException("Invalid email");
		Optional<People> result = peopleRepository.findByEmail(email);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@GetMapping("/getById")
	public People getPeopleById(@RequestBody People people) {
		final Long id = people.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		Optional<People> result = peopleRepository.findById(id);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@PostMapping("/addOne")
	public Long addOnePeople(@RequestBody People people) {
		final String email = people.getEmail();
		Validator.validatePeople(people);
		if(peopleRepository.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email already exists");
		return peopleRepository.save(people).getId();
	}
	
	
	@PutMapping("/update")
	public Long updatePeople(@RequestBody People people) {
		final Long id = people.getId();
		if(id == null || people.getName() == null || people.getEmail() == null) 
			throw new IllegalArgumentException("update: Null values found");
		if(!peopleRepository.findById(id).isPresent()) throw new IllegalArgumentException("Can't find person with id: " + id);
		return peopleRepository.save(people).getId();
	}
	
	
	@DeleteMapping("/deleteOne")
	public HttpStatus deleteOnePeople(@RequestBody People people) {
		final Long id = people.getId();
		if(id == null) return null;
		final Optional<People> result = peopleRepository.findById(id);
		if(!result.isPresent()) return null;
		peopleRepository.deleteById(id);
		return HttpStatus.OK;
	}
}
