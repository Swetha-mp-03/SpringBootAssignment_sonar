package com.example.department.assignment.service;

import com.example.department.assignment.entity.Department;

import java.util.List;

public interface  DepartmentService {
    Department save(Department dept);
    Department getById(Long id);
    List<Department> getAll();
    Department update(Long id,Department department);
    void delete(Long id);
}
