package com.yash.demo.dao;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyLong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.yash.demo.exception.RecordNotFoundException;
import com.yash.demo.model.Employee;
import com.yash.demo.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeDaoTest {

	@InjectMocks
    private EmployeeDao empDao;
	
	@Mock
	private EmployeeRepository employeeRepositoryMock;
	
	@Test
	public void shouldGetAllEmployees() {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));
		Employee secondEmp = new Employee(2L, "pqr", "trainee engineer", "java", new Date(2323223232L));
        List<Employee> expectedEmployeeList = new ArrayList<Employee>() ;
        expectedEmployeeList.add(firstEmp);
        expectedEmployeeList.add(secondEmp);
		
		when(employeeRepositoryMock.findAll()).thenReturn(Arrays.asList(firstEmp, secondEmp));

		List<Employee> actualEmployeeList = empDao.findAll();
		
		assertEquals(expectedEmployeeList, actualEmployeeList);
		
		verify(employeeRepositoryMock, times(1)).findAll();
		
	}
	
	@Test
	public void shouldGetDetailsOfEmployeeOne() {
		Optional<Employee> firstEmp = Optional.of( new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L)));

		when(employeeRepositoryMock.findById(1L)).thenReturn(firstEmp);
		
		Employee actualEmp = empDao.findOne(1L); 
	 
		assertEquals(firstEmp.get().getName(),actualEmp.getName());
		
		verify(employeeRepositoryMock, times(1)).findById(1L);
	}
	
//	@Test(expected = RecordNotFoundException.class)
//	public void shouldThrowRecordNotFoundExceptionIfRecordNotFound() {
//		
//		//Optional<Employee> firstEmp = Optional.of( new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L)));
//
//
//		when(employeeRepositoryMock.findById(10000L)).thenThrow(RecordNotFoundException.class);
//		
//		//Employee emp = 
//		empDao.findOne(10000L);
//		
//
//	}
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	  
	@Test
	public void shouldThrowRecordNotFoundExceptionIfRecordNotFound() {
		
		exceptionRule.expect(RecordNotFoundException.class);
	    //exceptionRule.expectMessage("Not Found");
		//when(employeeRepositoryMock.findById(anyLong())).thenThrow(new RecordNotFoundException("Record Not Found"));

		empDao.findOne(10000L);
	}
	
	@Test
	public void shouldSaveDetailsOfGivenEmployee() {
		Employee expectedEmployee = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));
   
		when(employeeRepositoryMock.save(expectedEmployee)).thenReturn(expectedEmployee);
		
		Employee actualEmployee = empDao.save(expectedEmployee);
		
		assertEquals(expectedEmployee, actualEmployee);

		verify(employeeRepositoryMock, times(1)).save(expectedEmployee);
	}
	
	@Test
	public void shoulDeleteDetailsOfGivenEmployee() {
		Employee expectedEmployee = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));
   
		doNothing().when(employeeRepositoryMock).delete(Mockito.any(Employee.class));
		
	    empDao.delete(expectedEmployee);

	    verify(employeeRepositoryMock, times(1)).delete(expectedEmployee);
	}
	
}
