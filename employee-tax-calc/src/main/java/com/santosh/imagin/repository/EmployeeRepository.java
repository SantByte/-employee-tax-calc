package com.santosh.imagin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santosh.imagin.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
