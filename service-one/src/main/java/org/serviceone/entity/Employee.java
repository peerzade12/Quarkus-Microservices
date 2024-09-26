package org.serviceone.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(database = "serviceone", collection = "employees")
public class Employee {

    private ObjectId id;

    private String name;

    private String email;

    private double salary;

    String departmentId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Employee() {
    }

    public Employee(ObjectId id, String name, String email, double salary, String departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
    }
}
