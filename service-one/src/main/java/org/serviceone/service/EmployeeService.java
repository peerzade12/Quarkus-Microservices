package org.serviceone.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.serviceone.entity.Employee;
import org.serviceone.exception.NoSuchElementFoundException;
import org.serviceone.exception.NoSuchIdFoundException;
import org.serviceone.repository.DepartmentRepository;
import org.serviceone.repository.EmployeeRepository;

import java.util.List;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    DepartmentRepository departmentRepository;

    public Uni<Employee> addNewEmployee(Employee employee) {
        return departmentRepository.find("_id", new ObjectId(employee.getDepartmentId()))
                .firstResult()
                .onItem().ifNotNull().transformToUni(employ -> employeeRepository.persist(employee))
                .onItem().ifNull().failWith(new NoSuchElementFoundException(Response.Status.NOT_FOUND.getStatusCode(), "Department with id " + employee.getDepartmentId() + " not found"));
    }

    public Uni<List<Employee>> getAllEmployees() {
        return employeeRepository.listAll();
    }

    public Uni<Employee> updateEmployee(String id, Employee employee) {
        return employeeRepository.find("_id", new ObjectId(id))
                .firstResult()
                .onItem()
                .ifNotNull()
                .transformToUni(existingPerson -> {
                    existingPerson.setName(employee.getName());
                    existingPerson.setEmail(employee.getEmail());
                    existingPerson.setSalary(employee.getSalary());
                    return employeeRepository.update(existingPerson);
                }).onItem().ifNull().failWith(new NoSuchIdFoundException(Response.Status.NOT_FOUND.getStatusCode(), "Employee with id " + id + " not found"));
    }

    public Uni<Boolean> deleteEmployee(String id) {
        return employeeRepository.find("_id", new ObjectId(id))
                .firstResult()
                .onItem().ifNotNull().transformToUni(existingEmployee -> {
                    return employeeRepository.deleteById(existingEmployee.getId())
                            .onItem().transform(deleted -> deleted);
                })
                .onItem().ifNull().failWith(new NoSuchIdFoundException(Response.Status.NOT_FOUND.getStatusCode(), "Employee with id " + id + " not found"));
    }
}
