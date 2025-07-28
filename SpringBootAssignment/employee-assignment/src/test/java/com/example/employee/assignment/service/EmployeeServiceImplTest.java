package com.example.employee.assignment.service;

import com.example.employee.assignment.dto.DepartmentDTO;
import com.example.employee.assignment.dto.EmployeeResponseDTO;
import com.example.employee.assignment.entity.Employee;
import com.example.employee.assignment.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl service;

    @Mock
    private EmployeeRepository repository;

    @Mock
    private RestTemplate restTemplate;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John", "Dev", "john@example.com", 50000.0, 101L);
    }

    @Test
    void testSave() {
        when(repository.save(employee)).thenReturn(employee);
        Employee saved = service.save(employee);
        assertEquals("John", saved.getName());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(employee));
        List<Employee> all = service.getAll();
        assertEquals(1, all.size());
    }

    @Test
    void testGetById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        Employee found = service.getById(1L);
        assertEquals("Dev", found.getRole());
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getById(2L));
        assertEquals("Employee with ID 2 is not found", ex.getMessage());

    }

    @Test
    void testUpdate() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(any(Employee.class))).thenReturn(employee);

        Employee update = new Employee();
        update.setName("Updated");
        update.setRole("DevOps");
        update.setEmail("updated@example.com");
        update.setSalary(75000.0);

        Employee updated = service.update(1L, update);
        assertEquals("Updated", updated.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testGetByDepartmentId() {
        when(repository.findByDepartmentId(101L)).thenReturn(List.of(employee));
        List<Employee> list = service.getEmployeesByDepartmentId(101L);
        assertEquals(1, list.size());
    }

    @Test
    void testGetEmployeeWithDepartment() {
        DepartmentDTO dept = new DepartmentDTO(101L, "IT", "NY");
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(restTemplate.getForObject(anyString(), eq(DepartmentDTO.class))).thenReturn(dept);

        EmployeeResponseDTO response = service.getEmployeeWithDepartment(1L);
        assertEquals("IT", response.getDepartment().getBranch());
    }
}
