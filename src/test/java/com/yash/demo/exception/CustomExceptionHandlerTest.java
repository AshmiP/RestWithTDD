package com.yash.demo.exception;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yash.demo.dao.EmployeeDao;

@RunWith(SpringRunner.class)
public class CustomExceptionHandlerTest {

	
	private MockMvc mockMvc;

    @Mock
    EmployeeDao empDaoMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(empDaoMock).setControllerAdvice(new CustomExceptionHandler())
            .build();
    }

    @Test//(groups = { "services" })
    public void testGlobalExceptionHandlerError() throws Exception {

        when(empDaoMock.findOne(10000L)).thenThrow(new RecordNotFoundException("RecordNotFoundException"));

        mockMvc.perform(get("/company/employees/10000")).andExpect(status().isNotFound()).andReturn();

    }

}
