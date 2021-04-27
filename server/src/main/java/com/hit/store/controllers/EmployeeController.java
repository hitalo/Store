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

import com.hit.store.models.Employee;
import com.hit.store.models.People;
import com.hit.store.repositories.EmployeeRepository;
import com.hit.store.repositories.PeopleRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private PeopleRepository peopleRepository;
	
	
	@GetMapping("/getAll")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	
	@GetMapping("/getById")
	public Employee getById(@RequestBody Employee employee) {
		final Long id = employee.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<Employee> result = employeeRepository.findById(id);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@PostMapping("/addWithNewPeople")
	public Long addEmployeeNewPeople(@RequestBody Employee employee) {
		final People people = employee.getPeople();
		Validator.validatePeople(people);
		peopleRepository.save(people);
		return employeeRepository.save(employee).getId();
	}
	
	
	@PostMapping("/addWithExistingPeople")
	public Long addEmployeeExistingPeople(@RequestBody Employee employee) {
		final People people = employee.getPeople();
		final Long peopleId = people.getId();
		
		if(peopleId == null) throw new IllegalArgumentException("people_id can't be null");
		final Optional<People> result = peopleRepository.findById(peopleId);
		if(!result.isPresent()) 
			throw new IllegalArgumentException("Personal data missing");
		
		if(employeeRepository.findByPeople(people).isPresent()) throw new IllegalArgumentException("Employee already exists");
		return employeeRepository.save(employee).getId();
	}
	
	
	@PutMapping("/update")
	public Long updateEmployee(@RequestBody Employee employee) {
		if(employee.getId() == null) throw new IllegalArgumentException("Can't update employee with null id");
		final People people = employee.getPeople();
		if(people.getId() == null) throw new IllegalArgumentException("Can't update employee with null people_id");
		Validator.validatePeople(people);
		peopleRepository.save(people);
		return employeeRepository.save(employee).getId();
	}
	
		
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public HashMap deleteEmployee(@RequestBody Employee employee) {
		final Long id = employee.getId();
		if(id == null) return null;
		final Optional<Employee> result = employeeRepository.findById(id);
		if(!result.isPresent()) return null;
		employeeRepository.deleteById(id);
		return new HashMap(){{put("deleted", 1);}};
	}
}
