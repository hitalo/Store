package com.hit.store.models.dto;

import java.util.List;

import com.hit.store.models.people.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {

	private Long roleId;
	private List<Permission> permissions;
	
}
