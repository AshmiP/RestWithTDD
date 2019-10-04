package com.yash.demo.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.demo.dao.EmployeeDao;
import com.yash.demo.exception.CustomExceptionHandler;
import com.yash.demo.exception.RecordNotFoundException;
import com.yash.demo.model.Employee;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeDao empDaoMock;

	

	@Test
	public void shouldGetAllEmployeesXML() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));
		Employee secondEmp = new Employee(2L, "pqr", "trainee engineer", "java", new Date(2323223232L));

		when(empDaoMock.findAll()).thenReturn(Arrays.asList(firstEmp, secondEmp));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/employees")
				.contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).findAll();
		verifyNoMoreInteractions(empDaoMock);
	}

	@Test
	public void shouldGetAllEmployeesJSON() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));
		Employee secondEmp = new Employee(2L, "pqr", "trainee engineer", "java", new Date(2323223232L));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/employees")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		when(empDaoMock.findAll()).thenReturn(Arrays.asList(firstEmp, secondEmp));

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).findAll();
		verifyNoMoreInteractions(empDaoMock);
	}

	@Test
	public void shouldGetEmployeeWithIdOneForJSON() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/employees/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		when(empDaoMock.findOne(1L)).thenReturn(firstEmp);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).findOne(1L);
		verifyNoMoreInteractions(empDaoMock);
	}

	@Test
	public void shouldGetEmployeeWithIdOneForXML() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/employees/1")
				.contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		when(empDaoMock.findOne(1L)).thenReturn(firstEmp);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).findOne(1L);
		verifyNoMoreInteractions(empDaoMock);
	}

	@Test
	public void shouldPostEmployeeWithGivenDetails() throws Exception {
		Employee firstEmp = new Employee("abcd", "software engineer", "java");

		Employee firstEmpResp = new Employee(1L, "abcd", "software engineer", "java");

		when(empDaoMock.save(firstEmp)).thenReturn(firstEmpResp);

		ObjectMapper objectMapper = new ObjectMapper();
		String empString = objectMapper.writeValueAsString(firstEmp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/employees").content(empString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		// doNothing().when(empDaoMock).save(Mockito.any(Employee.class));
		// MvcResult mvcResult =
		// mockMvcperform(requestBuilder).andReturn().andDo(print());
		// assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).save(firstEmp);
		verifyNoMoreInteractions(empDaoMock);
	}

	@Test
	public void shouldPutEmployeeWithGivenDetailsWhenIdIsOne() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java");

		Employee firstEmpResp = new Employee(1L, "abcd", "software engineer", "java");

		when(empDaoMock.save(firstEmp)).thenReturn(firstEmpResp);
		when(empDaoMock.findOne(1L)).thenReturn(firstEmp);

		ObjectMapper objectMapper = new ObjectMapper();
		String empString = objectMapper.writeValueAsString(firstEmp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/company/employees/1").content(empString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).save(firstEmp);
	}
 
	@Test
	public void shouldDeleteEmployeeWithGivenDetailsWhenIdIsOne() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java");

		when(empDaoMock.findOne(1L)).thenReturn(firstEmp);
		doNothing().when(empDaoMock).delete(Mockito.any(Employee.class));

		ObjectMapper objectMapper = new ObjectMapper();
		String empString = objectMapper.writeValueAsString(firstEmp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/employees/1").content(empString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

		verify(empDaoMock, times(1)).delete(firstEmp);
	}

	@Test 
	public void shouldThrowRecordNotFoundExceptionOnDeleteEmployeeWhenEmployeeNotFound() throws Exception {
		
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java");
		ObjectMapper objectMapper = new ObjectMapper();
		String empString = objectMapper.writeValueAsString(firstEmp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/employees/100000").content(empString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());

		//verify(empDaoMock, times(1)).delete(firstEmp);
	}
	@Test
	public void shouldThrowRecordNotFoundExceptionOnPutEmployeeWhenEmployeeNotFound() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java");

		Employee firstEmpResp = new Employee(1L, "abcd", "software engineer", "java");

		//when(empDaoMock.save(firstEmp)).thenReturn(firstEmpResp);
		//when(empDaoMock.findOne(1L)).thenReturn(firstEmp);

		ObjectMapper objectMapper = new ObjectMapper();
		String empString = objectMapper.writeValueAsString(firstEmp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/company/employees/10000").content(empString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());

		//verify(empDaoMock, times(1)).save(firstEmp);
	}	
	
	@Test
	public void shouldThrowRecordNotFoundExceptionOnFindOneEmployeeWhenEmployeeNotFound() throws Exception {
		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java");

		Employee firstEmpResp = new Employee(1L, "abcd", "software engineer", "java");

//		when(empDaoMock.save(firstEmp)).thenReturn(firstEmpResp);
//		when(empDaoMock.findOne(1L)).thenReturn(firstEmp);

		ObjectMapper objectMapper = new ObjectMapper();
		String empString = objectMapper.writeValueAsString(firstEmp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/company/employees/100000").content(empString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());

		//verify(empDaoMock, times(1)).save(firstEmp);
	}
	
//	@Test
//	public void shouldGetAllEmployees() throws Exception {
//		Employee firstEmp = new Employee(1L, "abcd", "software engineer", "java", new Date(2323223232L));
//		Employee secondEmp = new Employee(2L, "pqr", "trainee engineer", "java", new Date(2323223232L));
//		
//		when(empDaoMock.findAll()).thenReturn(Arrays.asList(firstEmp, secondEmp));
//	
//		
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/employees").contentType(MediaType.ALL).accept(MediaType.ALL);
//		
//		
//		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
//		
//		verify(empDaoMock, times(1)).findAll();
//	    verifyNoMoreInteractions(empDaoMock);  
//	}
}
