package com.hit.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.Employee;
import com.hit.store.models.People;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByPeople(People people);
}
