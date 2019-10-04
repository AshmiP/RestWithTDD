package com.yash.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.demo.model.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
