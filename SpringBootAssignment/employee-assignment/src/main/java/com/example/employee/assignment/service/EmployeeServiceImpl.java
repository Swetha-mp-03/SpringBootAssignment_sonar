package com.example.employee.assignment.service;
import com.example.employee.assignment.entity.Employee;
import com.example.employee.assignment.dto.DepartmentDTO;
import com.example.employee.assignment.dto.EmployeeResponseDTO;
import com.example.employee.assignment.exception.EmployeeNotFoundException;
import com.example.employee.assignment.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final RestTemplate restTemplate;

    public EmployeeServiceImpl(EmployeeRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " is not found"));
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee existing = getById(id);
        existing.setName(employee.getName());
        existing.setRole(employee.getRole());
        existing.setSalary(employee.getSalary());
        existing.setEmail(employee.getEmail());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return repository.findByDepartmentId(departmentId);
    }


    @Override
    public EmployeeResponseDTO getEmployeeWithDepartment(Long id) {
        Employee emp = getById(id);

        DepartmentDTO dept = restTemplate.getForObject(
                "http://localhost:8081/api/departments/ass/" + emp.getDepartmentId(),
                DepartmentDTO.class
        );

        return new EmployeeResponseDTO(
                emp.getId(),
                emp.getName(),
                emp.getRole(),
                emp.getEmail(),
                emp.getSalary(),
                dept
        );
    }
}
