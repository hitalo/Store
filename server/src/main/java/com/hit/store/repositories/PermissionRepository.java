package com.hit.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String>{
	List<Permission> findByNameIgnoreCaseContaining(String name);
}
