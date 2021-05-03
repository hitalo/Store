package com.hit.store.models.dto;

import java.util.List;

import com.hit.store.models.people.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

	private Long userId;
	private List<Role> roles;
	
}
