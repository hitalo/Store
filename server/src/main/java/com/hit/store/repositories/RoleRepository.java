package com.hit.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{}
