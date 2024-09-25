package org.serviceone.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.serviceone.entity.Employee;

@ApplicationScoped
public class EmployeeRepository implements ReactivePanacheMongoRepository<Employee> {
}
