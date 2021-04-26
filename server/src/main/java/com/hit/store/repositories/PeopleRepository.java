package com.hit.store.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hit.store.models.People;

public interface PeopleRepository extends JpaRepository<People, Long>{
	Optional<People> findByEmail(String email);
}