package org.serviceone.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.serviceone.entity.Employee;
import org.serviceone.repository.EmployeeRepository;

import java.util.List;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    public Uni<Employee> addNewEmployee(Employee employee) {
        return employeeRepository.persist(employee);
    }

    public Uni<List<Employee>> getAllEmployees() {
        return employeeRepository.listAll();
    }

    public Uni<Employee> updateEmployee(String id, Employee employee) {
        return employeeRepository.find("id", new ObjectId(id)).firstResult().onItem().ifNotNull().transformToUni(existingPerson -> {
            existingPerson.setName(employee.getName());
            existingPerson.setEmail(employee.getEmail());
            existingPerson.setSalary(employee.getSalary());
            return employeeRepository.update(existingPerson);
        });
    }

    public Uni<Boolean> deleteEmployee(String id) {
        return employeeRepository.deleteById(new ObjectId(id));
    }
}
