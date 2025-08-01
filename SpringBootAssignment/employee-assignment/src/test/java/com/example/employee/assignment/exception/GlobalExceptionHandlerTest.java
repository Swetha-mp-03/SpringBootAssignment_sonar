package com.example.employee.assignment.exception;

import com.example.employee.assignment.controller.EmployeeController;
import com.example.employee.assignment.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void testHandleEmployeeNotFoundException() throws Exception {
        Long employeeId = 999L;

        when(employeeService.getEmployeeWithDepartment(employeeId))
                .thenThrow(new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));

        mockMvc.perform(get("/api/employees/ass/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Employee with ID 999 not found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void testHandleGenericException() throws Exception {
        Long employeeId = 1000L;

        // Simulate an unexpected exception
        when(employeeService.getEmployeeWithDepartment(employeeId))
                .thenThrow(new RuntimeException("Unexpected error occurred"));

        mockMvc.perform(get("/api/employees/ass/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.message", is("Unexpected error occurred")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}
