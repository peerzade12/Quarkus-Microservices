package org.serviceone.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class MongoConnectionTest {

    @Inject
    MongoClient mongoClient;

    @Test
    public void testMongoConnection() {

        assertNotNull(mongoClient, "MongoClient should be injected and not null");

        // database checking
        MongoDatabase database = mongoClient.getDatabase("serviceone");
        assertNotNull(database, "Database should not be null");

    }
}
