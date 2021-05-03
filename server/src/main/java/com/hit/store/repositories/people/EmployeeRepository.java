package com.hit.store.repositories.people;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.people.Employee;
import com.hit.store.models.people.People;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByPeople(People people);
}
