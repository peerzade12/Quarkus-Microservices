package org.serviceone.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.serviceone.entity.Department;

@ApplicationScoped
public class DepartmentRepository implements ReactivePanacheMongoRepository<Department> {
}
