package org.serviceone.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.serviceone.entity.Department;
import org.serviceone.exception.NoSuchIdFoundException;
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
        return departmentRepository.find("_id", new ObjectId(id))
                .firstResult()
                .onItem().ifNotNull().transformToUni(existingDept -> departmentRepository.deleteById(existingDept.getDepartmentId())
                        .onItem().transform(deleted -> deleted))
                .onItem().ifNull().failWith(new NoSuchIdFoundException(Response.Status.NOT_FOUND.getStatusCode(), "Department with id " + id + " not found"));

    }
}
