package com.hit.store.repositories.people;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.people.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{}
