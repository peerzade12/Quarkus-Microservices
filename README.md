# Quarkus-Microservices

Pre-requisite:
1. install MongoDB
2. Any IDE
3. Install JDK
   
Implementation of two microservices using Quarkus Framework.

1. Microservice service-one: Implements three endpoints and interceptor adding a custom header.
2. Microservice service-two: Acts as a Client service and call service-one endpoints.
  - It implements fault tolerance, exception handling, filter to log info for request-response.

#Setup
1. Clone the Repository: open terminal and write "git clone https://github.com/peerzade12/Quarkus-Microservices.git"
2. Navigate to the this directory and go to Quarkus-Microservices directory. Here you will find service-one and service-two.
4. Open service-one in the Editor(e.g IntelliJ), open terminal and type the following commands
   "./mvnw clean install", followed by "./mvnw quarkus:dev".
   NOTE: service-one will be running on http://localhost:8080, you can find the swagger-ui documentation here http://localhost:8080/q/swagger-ui/
5. Repeat step 4 for service-two.
   NOTE: service-two will be running on http://localhost:9000, you can find the swagger-ui documentation here http://localhost:9000/q/swagger-ui/
6. For testing endpoints on postman: Open postman and import Quarkus-Microservice-Asfiya.postman_collection.json from Quarkus-Microservices folder.
There you go :)
 
