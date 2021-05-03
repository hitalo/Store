package com.hit.store.utils;

import com.hit.store.models.people.People;
import com.hit.store.models.people.Permission;

public class Validator {

	public static boolean isEmailValid(String email) {
		String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		return email.matches(regex);
	}

	
	public static boolean isPasswordValid(String password) {
		return password.length() > 5 ? true : false;			//  for now, thats the only condition 
	}
	
	
	public static boolean validatePeople(People people) {
		final String email = people.getEmail();
		if(!Validator.isEmailValid(email)) throw new IllegalArgumentException("Invalid email");
		if(people.getName().trim().equals("")) throw new IllegalArgumentException("Invalid name");
		return true;
	}
	
	
	public static boolean validatePermission(Permission permission) {
		final String value = permission.getValue();
		final String name = permission.getName();
		if(value == null || value.trim().equals("")) throw new IllegalArgumentException("Invalid value");
		if(name == null || name.trim().equals("")) throw new IllegalArgumentException("Invalid name");
		return true;
	}
}
