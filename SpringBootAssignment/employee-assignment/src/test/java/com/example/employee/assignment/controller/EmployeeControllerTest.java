package com.example.employee.assignment.controller;

import com.example.employee.assignment.dto.DepartmentDTO;
import com.example.employee.assignment.dto.EmployeeResponseDTO;
import com.example.employee.assignment.entity.Employee;
import com.example.employee.assignment.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final Employee sampleEmployee = new Employee(1L, "John", "Developer", "john@example.com", 60000.0, 101L);

    @Test
    void testCreate() throws Exception {
        when(service.save(any(Employee.class))).thenReturn(sampleEmployee);

        mockMvc.perform(post("/api/employees/ass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(sampleEmployee));

        mockMvc.perform(get("/api/employees/ass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetById() throws Exception {
        EmployeeResponseDTO response = new EmployeeResponseDTO(1L, "John", "Developer", "john@example.com", 60000.0,
                new DepartmentDTO(101L, "IT", "New York"));
        when(service.getEmployeeWithDepartment(1L)).thenReturn(response);

        mockMvc.perform(get("/api/employees/ass/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.department.branch").value("IT"));
    }

    @Test
    void testGetByDepartmentId() throws Exception {
        when(service.getEmployeesByDepartmentId(101L)).thenReturn(List.of(sampleEmployee));

        mockMvc.perform(get("/api/employees/ass/department/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testUpdate() throws Exception {
        Employee updated = new Employee(1L, "John Updated", "Developer", "john@example.com", 70000.0, 101L);
        when(service.update(Mockito.eq(1L), any(Employee.class))).thenReturn(updated);

        mockMvc.perform(put("/api/employees/ass/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void testDelete() throws Exception {
        Mockito.doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/employees/ass/1"))
                .andExpect(status().isOk());
    }
}
