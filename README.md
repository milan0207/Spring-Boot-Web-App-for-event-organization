# Spring Boot Web Application

This is a full-stack Spring Boot web application designed to manage multiple entities with complex relationships. The application supports multiple profiles, integrates Redis caching for enhanced performance, and offers flexible database access using both in-memory custom JDBC implementation and JPA.

## Key Features:
- **Multiple Entities with Relationships**: The application models and manages various entities, defining relationships such as One-to-Many, Many-to-One, and Many-to-Many between them.
- **Redis Caching**: Utilizes Redis caching to improve the performance of frequently accessed data, reducing load on the database and enhancing response times.
- **Database Access**: Supports multiple profiles:
  - **In-Memory Custom JDBC**: For lightweight, fast, and temporary database interactions.
  - **JPA-based Database Access**: For more persistent and scalable data storage using JPA repositories.
- **Profiles**: Seamlessly switch between different profiles (e.g., development, production) to configure the appropriate database and caching strategy.
- **Exception Handling**: Centralized error handling, using Spring’s exception handling mechanisms for consistent and clean error responses.
- **Authorization and Authentication**: Implements basic security mechanisms for access control to sensitive endpoints.
- **Scheduled Tasks**: Incorporates scheduled tasks for automatic periodic maintenance, such as cleaning up outdated data.

## Technologies Used:
- **Spring Boot**: Core framework for building the web application.
- **Redis**: For caching frequently accessed data and improving system performance.
- **JPA (Java Persistence API)**: For database interaction with entities stored in a relational database.
- **Spring Scheduler**: For implementing and managing scheduled tasks.
- **Custom JDBC Implementation**: For an in-memory, lightweight database solution.
