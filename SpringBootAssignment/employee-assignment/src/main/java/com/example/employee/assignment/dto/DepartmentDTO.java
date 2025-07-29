package com.example.employee.assignment.dto;

public class DepartmentDTO {
    private Long id;
    private String branch;
    private String location;

    // No-args constructor
    public DepartmentDTO() {
    }

    // All-args constructor
    public DepartmentDTO(Long id, String branch, String location) {
        this.id = id;
        this.branch = branch;
        this.location = location;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getBranch() {
        return branch;
    }

    public String getLocation() {
        return location;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
