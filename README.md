# Task Management API

A backend REST API built with **Java 21**, **Spring Boot**, and **Maven** for managing users, projects, and tasks. The project demonstrates a layered backend architecture with Spring Data JPA, MySQL, DTO-based request/response models, validation, centralized exception handling, Swagger documentation, Docker Compose, and unit testing.

## Tech Stack

* Java 21
* Spring Boot
* Maven
* Spring Web
* Spring Data JPA
* MySQL
* Spring Security
* Bean Validation
* Lombok
* Swagger / OpenAPI
* JUnit 5
* Mockito
* Docker Compose

## Features

* User management
* Project management
* Task management
* Task assignment to users
* Task status update
* Task priority update
* Task filtering by status, priority, project, and assigned user
* Dashboard statistics
* DTO-based API responses
* Bean Validation for request validation
* Centralized exception handling
* Swagger/OpenAPI documentation
* Dockerized MySQL setup
* Unit tests for core business logic

## Project Structure

```text
src/main/java/com/taskmanagmentapi
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
└── service
```

## Architecture

The project follows a layered backend architecture:

```text
Controller → Service → Repository → Database
```

### Controller Layer

Handles HTTP requests and responses.

### Service Layer

Contains business logic such as task assignment rules, status transitions, and dashboard calculations.

### Repository Layer

Provides database access using Spring Data JPA.

### DTO Layer

Separates API request/response models from entity classes.

## Database Model

### User

Represents a system user.

```text
id
firstName
lastName
email
password
role
createdAt
```

### Project

Represents a project that contains tasks.

```text
id
name
description
createdAt
```

### Task

Represents a task under a project.

```text
id
title
description
status
priority
deadline
createdAt
updatedAt
assignedUser
project
```

## Entity Relationships

```text
User 1 ──── N Task
Project 1 ──── N Task
Task N ──── 1 User
Task N ──── 1 Project
```

## Task Status Values

```text
TODO
IN_PROGRESS
DONE
CANCELLED
```

## Task Priority Values

```text
LOW
MEDIUM
HIGH
```

## Business Rules

* A cancelled task cannot be assigned to a user.
* A completed task cannot be moved back to TODO.
* A task with an expired deadline cannot be marked as DONE.
* A user cannot have more than 20 active tasks.
* Deleting a project also deletes its related tasks.

## API Endpoints

### Health Check

```http
GET /health
```

### Users

```http
POST   /api/v1/users
GET    /api/v1/users
GET    /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

### Projects

```http
POST   /api/v1/projects
GET    /api/v1/projects
GET    /api/v1/projects/{id}
PUT    /api/v1/projects/{id}
DELETE /api/v1/projects/{id}
```

### Tasks

```http
POST   /api/v1/tasks
GET    /api/v1/tasks
GET    /api/v1/tasks/{id}
DELETE /api/v1/tasks/{id}
PATCH  /api/v1/tasks/{id}/assign
PATCH  /api/v1/tasks/{id}/status
PATCH  /api/v1/tasks/{id}/priority
```

### Dashboard

```http
GET /api/v1/dashboard
```

## Filtering Examples

```http
GET /api/v1/tasks?status=TODO
GET /api/v1/tasks?priority=HIGH
GET /api/v1/tasks?projectId=1
GET /api/v1/tasks?assignedUserId=2
```

## Example Requests

### Create User

```http
POST /api/v1/users
```

```json
{
  "firstName": "Mert",
  "lastName": "Kocoglu",
  "email": "mert@example.com",
  "password": "123456",
  "role": "ADMIN"
}
```

### Create Project

```http
POST /api/v1/projects
```

```json
{
  "name": "Backend Development",
  "description": "Spring Boot task management project"
}
```

### Create Task

```http
POST /api/v1/tasks
```

```json
{
  "title": "Implement task assignment",
  "description": "Assign tasks to users",
  "priority": "HIGH",
  "deadline": "2026-12-31",
  "projectId": 1,
  "assignedUserId": 1
}
```

### Assign Task

```http
PATCH /api/v1/tasks/1/assign
```

```json
{
  "userId": 1
}
```

### Update Task Status

```http
PATCH /api/v1/tasks/1/status
```

```json
{
  "status": "IN_PROGRESS"
}
```

### Update Task Priority

```http
PATCH /api/v1/tasks/1/priority
```

```json
{
  "priority": "MEDIUM"
}
```

## Running Locally

### Prerequisites

* Java 21
* Docker
* Maven or Maven Wrapper

### 1. Clone the Repository

```bash
git clone https://github.com/MertKocoglu/Task-Management-API.git
cd Task-Management-API
```

### 2. Start MySQL with Docker Compose

```bash
docker compose up -d
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will run on:

```text
http://localhost:8080
```

## Application Configuration

Example `application.yml`:

```yaml
spring:
  application:
    name: task-management-api

  datasource:
    url: jdbc:mysql://localhost:3306/task_management
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080
```

## Swagger Documentation

After running the application, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Running Tests

```bash
./mvnw test
```

## Future Improvements

* JWT authentication
* Role-based authorization
* Pagination and sorting
* Advanced filtering with JPA Specification
* Integration tests with Testcontainers
* GitHub Actions CI pipeline
* Dockerfile for the Spring Boot application

## Author

**Mert Kocoglu**

GitHub: [MertKocoglu](https://github.com/MertKocoglu)
