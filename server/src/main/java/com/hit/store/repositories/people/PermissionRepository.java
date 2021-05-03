package com.hit.store.repositories.people;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.people.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String>{
	List<Permission> findByNameIgnoreCaseContaining(String name);
}
