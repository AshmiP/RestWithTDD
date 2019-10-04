package com.yash.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.yash.demo.exception.RecordNotFoundException;
import com.yash.demo.model.Employee;
import com.yash.demo.repository.EmployeeRepository;

@Service
public class EmployeeDao {
	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee save(Employee emp) {
		return employeeRepository.save(emp);
	}
		
	public List<Employee> findAll(){
		return employeeRepository.findAll();
	}
	
	public Employee findOne(Long empid) {
		
		Optional<Employee> emp = employeeRepository.findById(empid);
		if(emp.isPresent()) {
			return emp.get();
		}
		else
		  throw new RecordNotFoundException("Invalid employee id :"+empid+" RECORD NOT FOUND....");
		
     }
		
	public void delete(Employee emp) {
		employeeRepository.delete(emp);
	}


}
