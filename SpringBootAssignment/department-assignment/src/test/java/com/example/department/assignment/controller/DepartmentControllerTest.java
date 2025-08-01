package com.example.department.assignment.controller;

import com.example.department.assignment.entity.Department;
import com.example.department.assignment.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        department1 = new Department(1L, "IT", "Hyderabad");
        department2 = new Department(2L, "HR", "Bangalore");
    }

    @Test
    void testSave() throws Exception {
        when(service.save(any(Department.class))).thenReturn(department1);

        mockMvc.perform(post("/api/departments/ass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department1.getId()))
                .andExpect(jsonPath("$.branch").value(department1.getBranch()))
                .andExpect(jsonPath("$.location").value(department1.getLocation()));

        verify(service, times(1)).save(any(Department.class));
    }

    @Test
    void testGetAll() throws Exception {
        List<Department> departments = Arrays.asList(department1, department2);
        when(service.getAll()).thenReturn(departments);

        mockMvc.perform(get("/api/departments/ass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(departments.size()));

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(1L)).thenReturn(department1);

        mockMvc.perform(get("/api/departments/ass/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department1.getId()))
                .andExpect(jsonPath("$.branch").value(department1.getBranch()))
                .andExpect(jsonPath("$.location").value(department1.getLocation()));

        verify(service, times(1)).getById(1L);
    }

    @Test
    void testUpdate() throws Exception {
        Department updated = new Department(1L, "Finance", "Pune");
        when(service.update(eq(1L), any(Department.class))).thenReturn(updated);

        mockMvc.perform(put("/api/departments/ass/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branch").value(updated.getBranch()))
                .andExpect(jsonPath("$.location").value(updated.getLocation()));

        verify(service, times(1)).update(eq(1L), any(Department.class));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/departments/ass/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }
}
