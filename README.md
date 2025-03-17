# Simple Pizza App

# Overview
This Simple Pizza App is a small Spring Boot application designed for hands-on learning in
UI automation and API testing. It provides a basic user interface for interacting with the application,
making it ideal for those practicing UI automation testing. 
Additionally, it includes Swagger UI for easy API exploration and testing.

# Key Features:
 * Simple UI for users to interact with the application, making it suitable for UI automation testing.
 * Swagger integration to test and explore APIs interactively.
 * Spring Boot & Hibernate for backend development with a relational database.
 * MySQL support for data storage (configurable via application.properties).



# Prerequisites

## Tools & Software

To run this project locally, you wil need the following tools and software installed:
1. Download and install version 21 of the Java SE Platform (**JDK 21**)
   1. This project requires JDK 21 for building and running the Spring Boot application
   2. Download and install JDK 21 from [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/)
2. Download and install **Maven** for building and managing dependencies from [Maven Downloads](https://maven.apache.org/download.cgi)
3. Download and install **MySQL** or another relational DB management system  [MySQL Downloads](https://www.mysql.com/downloads/)
   1. Create a new MySQL Connection(credentials for the connection are in the application.properties)
   2. Create a new schema with name matching the one in application.properties
   * Currently the "spring.jpa.hibernate.ddl-auto" is set to "update" which reflects entity models in the DB tables.
   Consider changing it so you don't accidentally break your DB.

# Getting Started
1. Configure the Database
   1. Open "src/main/resources/application.properties"
   2. Update database credentials to match your MySQL setup:
       * spring.datasource.url
       * spring.datasource.username
       * spring.datasource.password 
   3. Ensure the schema name matches the one in application.properties.
2. Build and run the application
   1. Using maven 
      * mvn clean install
      * mvn spring-boot:run
      * To close terminate, CTRL + C in the terminal

* The first run will download all dependencies, create database tables automatically, and start the application.
* Once started, access:
  * UI: http://localhost:7070
  * Swagger API Docs: http://localhost:7070/swagger-ui.html
* You can login using the admin user with credentials (Created in DataInitializer)
   * username: admin
   * password: admin123