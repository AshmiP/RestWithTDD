package com.yash.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.demo.model.*;
import com.yash.demo.dao.*;

@RestController
@RequestMapping("/company")
public class EmployeeController {

	@Autowired
	EmployeeDao employeeDAO;

	/* to save an employee */
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee emp) {
		return employeeDAO.save(emp);
	}

	/* get all employees */
	@GetMapping(path = "/employees", produces = { "application/xml", "application/json" })
	public List<Employee> getAllEmployees() {

		return employeeDAO.findAll();
	}

	/* get employee by empid */
	@GetMapping(path ="/employees/{id}",produces = { "application/xml", "application/json" })
	public Resource<Employee> getEmployeeById(@PathVariable(value = "id") Long empid) {

		Resource<Employee> resource = new Resource<Employee>(employeeDAO.findOne(empid));

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllEmployees());
		ControllerLinkBuilder linkToo = linkTo(methodOn(this.getClass()).deleteEmployee(empid));

		resource.add(linkTo.withRel("all Emp"));
		resource.add(linkToo.withRel("delete emp"));

		if (resource.hasLinks()) {
			return resource;

		}
		return null;

	}

	/* update an employee by empid */
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long empid,
			@Valid @RequestBody Employee empDetails) {

		Employee emp = employeeDAO.findOne(empid);
		if (emp == null) {
			return ResponseEntity.notFound().build();
		}

		emp.setName(empDetails.getName());
		emp.setDesignation(empDetails.getDesignation());
		emp.setExpertise(empDetails.getExpertise());

		Employee updateEmployee = employeeDAO.save(emp);

		return ResponseEntity.ok().body(updateEmployee);

	}

	/* Delete an employee */
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") Long empid) {

		Employee emp = employeeDAO.findOne(empid);
		if (emp == null) {
			return ResponseEntity.notFound().build();
		}
		employeeDAO.delete(emp);

		return ResponseEntity.ok().build();

	}

}
