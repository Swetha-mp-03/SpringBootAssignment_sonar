package com.example.employee.assignment.controller;

import com.example.employee.assignment.exception.EmployeeNotFoundException;
import com.example.employee.assignment.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    private EmployeeService employeeService;

    @Test
    public void testEmployeeNotFoundException() throws Exception {
        Long employeeId = 100L;

        // Mock service to throw the exception
        when(employeeService.getEmployeeWithDepartment(employeeId))
                .thenThrow(new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));

        mockMvc.perform(get("/api/employees/ass/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Employee with ID 100 not found")))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}
