package com.example.employee.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDTOTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        DepartmentDTO dto = new DepartmentDTO(1L, "IT", "New York");

        assertEquals(1L, dto.getId());
        assertEquals("IT", dto.getBranch());
        assertEquals("New York", dto.getLocation());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        DepartmentDTO dto = new DepartmentDTO();

        dto.setId(2L);
        dto.setBranch("HR");
        dto.setLocation("London");

        assertEquals(2L, dto.getId());
        assertEquals("HR", dto.getBranch());
        assertEquals("London", dto.getLocation());
    }
}
