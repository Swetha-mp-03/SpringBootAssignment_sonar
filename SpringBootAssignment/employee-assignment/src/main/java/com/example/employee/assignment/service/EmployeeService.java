package com.example.employee.assignment.service;

import com.example.employee.assignment.entity.Employee;
import com.example.employee.assignment.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> getAll();
    Employee getById(Long id);
    Employee update(Long id,Employee employee);
    void delete(Long id);

    EmployeeResponseDTO getEmployeeWithDepartment(Long id);
    List<Employee> getEmployeesByDepartmentId(Long departmentId);


}
