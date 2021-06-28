# Sports Depth Chart Demo

### Design
Spring boot app generated from spring boot starter page:

* Spring jpa is used to save data to H2 in memory database.
* Unit test for the service and controllers are included, as well as an integration test
* The main logic around depth chart generation is in DepthChartService

### To build and execute
This is a maven based project.

Assuming maven is installed

* To package and run unit tests, "mvn clean package"
* To run from maven, "mvn spring-boot:run"

Server starts on port 8080 by default.

### To explore application apis

SwaggerUi is installed, so once the application is started, can go here:

http://localhost:8080/swagger-ui/

A copy of the swagger is also available at api-docs.json in the same folder as this readme.

