package com.hit.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.People;
import com.hit.store.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByPeople(People people);
}
