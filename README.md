# Football Manager App

A simple football manager application built with Spring Boot.  
This project uses an embedded **H2 database** and supports basic team and player operations via REST API.

## 🚀 Getting Started

To run the application locally:

```bash
./mvnw spring-boot:run
```
The application will start on:
```
http://localhost:8080
```
## 📮 API Collection
A complete Postman collection with example requests can be found here:

🔗 [Open in Postman](https://www.postman.com/essur/workspace/public-workspace/collection/24976968-9a4c3c66-6540-455c-a886-c7884b0073f5?action=share&source=copy-link&creator=24976968)



## 🗃️ Database

This project uses an in-memory H2 database.

H2 Console available at:
```
http://localhost:8080/h2-console
```
Default JDBC URL:
```
jdbc:h2:mem:testdb
```
The database is automatically pre-populated using the data.sql file on startup.
