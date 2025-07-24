package com.example.employee.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeResponseDTOTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        DepartmentDTO dept = new DepartmentDTO(1L, "Engineering", "Bangalore");

        EmployeeResponseDTO dto = new EmployeeResponseDTO(
                100L,
                "Alice",
                "Developer",
                "alice@example.com",
                90000.0,
                dept
        );

        assertEquals(100L, dto.getId());
        assertEquals("Alice", dto.getName());
        assertEquals("Developer", dto.getRole());
        assertEquals("alice@example.com", dto.getEmail());
        assertEquals(90000.0, dto.getSalary());
        assertEquals(dept, dto.getDepartment());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        DepartmentDTO dept = new DepartmentDTO();
        dept.setId(2L);
        dept.setBranch("Finance");
        dept.setLocation("Delhi");

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(101L);
        dto.setName("Bob");
        dto.setRole("Analyst");
        dto.setEmail("bob@example.com");
        dto.setSalary(75000.0);
        dto.setDepartment(dept);

        assertEquals(101L, dto.getId());
        assertEquals("Bob", dto.getName());
        assertEquals("Analyst", dto.getRole());
        assertEquals("bob@example.com", dto.getEmail());
        assertEquals(75000.0, dto.getSalary());
        assertEquals(dept, dto.getDepartment());
    }
}
