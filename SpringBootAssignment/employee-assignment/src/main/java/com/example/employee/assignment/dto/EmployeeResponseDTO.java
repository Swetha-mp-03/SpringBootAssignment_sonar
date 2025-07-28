package com.example.employee.assignment.dto;
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String role;
    private String email;
    private Double salary;
    private DepartmentDTO department;


    public EmployeeResponseDTO() {
    }


    public EmployeeResponseDTO(Long id, String name, String role, String email, Double salary, DepartmentDTO department) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }
}
