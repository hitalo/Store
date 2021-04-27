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

import com.hit.store.models.Permission;
import com.hit.store.repositories.PermissionRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionRepository permissionRepository;
	
	
	@GetMapping("/getAll")
	public List<Permission> getAll() {
		return permissionRepository.findAll();
	}
	
	
	@GetMapping("/getByValue")
	public Permission getByValue(@RequestBody Permission permission) {
		final String value = permission.getValue();
		if(value == null || value.trim().equals("")) throw new IllegalArgumentException("Value can't be null");
		final Optional<Permission> result = permissionRepository.findById(value);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@GetMapping("/getByName")
	public List<Permission> getByName(@RequestBody Permission permission) {
		final String name = permission.getName();
		if(name == null || name.trim().equals("")) return null;
		final List<Permission> result = permissionRepository.findByNameIgnoreCaseContaining(name);
		return result;
	}
	
	
	@PostMapping("/addOne")
	public String addOnePermission(@RequestBody Permission permission) {
		Validator.validatePermission(permission);
		final Optional<Permission> result = permissionRepository.findById(permission.getValue());
		if(result.isPresent()) throw new IllegalArgumentException("Permission already exists");
		return permissionRepository.save(permission).getValue();
	}
	
	
	@PutMapping("/update")
	public String updatePermission(@RequestBody Permission permission) {
		Validator.validatePermission(permission);
		return permissionRepository.save(permission).getValue();
	}
	
	
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public HashMap deletePermission(@RequestBody Permission permission) {
		final String value = permission.getValue();
		if(value == null || value.trim().equals("")) return null;
		final Optional<Permission> result = permissionRepository.findById(value);
		if(!result.isPresent()) return null;
		permissionRepository.deleteById(value);
		return new HashMap(){{put("deleted",1);}};
	}
}
