package com.hit.store.utils;

import com.hit.store.models.people.People;
import com.hit.store.models.people.Permission;
import com.hit.store.models.product.Cart;
import com.hit.store.models.product.Product;
import com.hit.store.models.product.Tag;

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
	
	
	public static boolean validateProduct(Product product) {
		final String name = product.getName();
		if(name == null || name.trim().equals("")) throw new IllegalArgumentException("Invalid name");
		if(product.getValue() < 0) throw new IllegalArgumentException("Invalid value");
		final String unit = product.getUnit();
		if(unit == null || unit.trim().equals("")) throw new IllegalArgumentException("Invalid unit");
		return true;
	}
	
	
	public static boolean validateTag(Tag tag) {
		final String name = tag.getName();
		if(name == null || name.trim().equals("")) throw new IllegalArgumentException("Invalid name");
		return true;
	}
	
	
	public static boolean validateCart(Cart cart) {
		if(cart.getUser().getId() == null) throw new IllegalArgumentException("user.id can't be null");
		if(cart.getProduct().getId() == null) throw new IllegalArgumentException("product.id can't be null");
		final Double amount = cart.getAmount();
		if(amount == null || amount < 0) throw new IllegalArgumentException("Invalid amount");
		return true;
	}
}
