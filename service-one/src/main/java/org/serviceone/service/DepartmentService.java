package org.serviceone.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.serviceone.entity.Department;
import org.serviceone.repository.DepartmentRepository;

import java.util.List;

@ApplicationScoped
public class DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    public Uni<Department> addNewDepartment(Department department) {
        return departmentRepository.persist(department);
    }

    public Uni<List<Department>> getAllDepartment() {
        return departmentRepository.listAll();
    }

    public Uni<Boolean> deleteDepartment(String id) {
        return departmentRepository.deleteById(new ObjectId(id));
    }
}
