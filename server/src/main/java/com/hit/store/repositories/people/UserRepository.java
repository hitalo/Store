package com.hit.store.repositories.people;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.people.People;
import com.hit.store.models.people.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByPeople(People people);
	Optional<User> findByLogin(String login);
}
