package com.example.department.assignment.service;

import com.example.department.assignment.entity.Department;
import com.example.department.assignment.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl service;

    @Mock
    private DepartmentRepository repo;

    private Department dept1, dept2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dept1 = new Department(1L, "Engineering", "New York");
        dept2 = new Department(2L, "HR", "Chicago");
    }

    @Test
    void testSave() {
        when(repo.save(dept1)).thenReturn(dept1);
        Department saved = service.save(dept1);
        assertEquals(dept1, saved);
        verify(repo).save(dept1);
    }

    @Test
    void testGetById_Found() {
        when(repo.findById(1L)).thenReturn(Optional.of(dept1));
        Department found = service.getById(1L);
        assertEquals("Engineering", found.getBranch());
    }

    @Test
    void testGetById_NotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertNull(service.getById(99L));
    }

    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(List.of(dept1, dept2));
        List<Department> list = service.getAll();
        assertEquals(2, list.size());
    }

    @Test
    void testUpdate_Found() {
        Department updated = new Department(null, "Updated", "Boston");
        when(repo.findById(1L)).thenReturn(Optional.of(dept1));
        when(repo.save(any(Department.class))).thenAnswer(inv -> inv.getArgument(0));

        Department result = service.update(1L, updated);

        assertNotNull(result);
        assertEquals("Updated", result.getBranch());
        assertEquals("Boston", result.getLocation());
        verify(repo).save(any(Department.class));
    }

    @Test
    void testUpdate_NotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Department updated = new Department(null, "Updated", "Boston");
        assertNull(service.update(99L, updated));
        verify(repo, never()).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(repo).deleteById(1L);
        service.delete(1L);
        verify(repo).deleteById(1L);
    }
}
