package com.example.department.assignment.service;


import com.example.department.assignment.entity.Department;
import com.example.department.assignment.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repo;

    public DepartmentServiceImpl(DepartmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Department save(Department dept){
        return repo.save(dept);
    }

    @Override
    public Department getById(Long id){
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Department> getAll(){
        return repo.findAll();
    }

    @Override
    public Department update(Long id,Department department){
        Department existing = repo.findById(id).orElse(null);
        if(existing != null){
            existing.setBranch(department.getBranch());
            existing.setLocation(department.getLocation());
            return repo.save(existing);
        }
        return null;
    }

    @Override
    public void delete(Long id){
        repo.deleteById(id);
    }

}
