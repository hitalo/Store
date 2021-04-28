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

import com.hit.store.models.Role;
import com.hit.store.repositories.RoleRepository;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	
	@GetMapping("/getAll")
	public List<Role> getAll() {
		return roleRepository.findAll();
	}
	
	
	@GetMapping("/getById")
	public Role getById(@RequestBody Role role) {
		final Long id = role.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<Role> result = roleRepository.findById(id);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@PostMapping("/addOne")
	public Long addOneRole(@RequestBody Role role) {
		final String name = role.getName(); 
		if(name == null || name.trim().equals("")) throw new IllegalArgumentException("Invalid name");
		return roleRepository.save(role).getId();
	}
	
	
	@PutMapping("/update")
	public Long updateRole(@RequestBody Role role) {
		final Long id = role.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final String name = role.getName(); 
		if(name == null || name.trim().equals("")) throw new IllegalArgumentException("Invalid name");
		final Optional<Role> result = roleRepository.findById(id);
		if(!result.isPresent()) throw new IllegalArgumentException("Can't find role with id: " + id);
		return roleRepository.save(role).getId();
	}
	
	
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public HashMap deleteRole(@RequestBody Role role) {
		final Long id = role.getId();
		if(id == null) return null;
		final Optional<Role> result = roleRepository.findById(id);
		if(!result.isPresent()) return null;
		roleRepository.deleteById(id);
		return new HashMap(){{put("deleted",1);}};
	}
}
