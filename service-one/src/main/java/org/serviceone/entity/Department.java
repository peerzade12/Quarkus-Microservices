package org.serviceone.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.io.Serializable;

@MongoEntity(collection = "department")
public class Department extends ReactivePanacheMongoEntityBase implements Serializable {

    @BsonId
    public ObjectId departmentId;

    public String departmentName;

    public ObjectId getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(ObjectId departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department() {
    }

    public Department(ObjectId departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

}
