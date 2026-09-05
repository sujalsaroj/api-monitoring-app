# API Monitoring & Health Dashboard

A full-stack API monitoring application built with **Spring Boot** and **React** that allows users to register APIs, automatically monitor their health, track response times, view monitoring history, and analyze uptime statistics.

The application uses **JWT authentication** to provide secure, user-specific API monitoring.

---

## Features

### Authentication & Security

- User registration and login
- JWT-based authentication
- BCrypt password encryption
- Protected backend endpoints
- Protected React routes
- User-specific API ownership
- Automatic handling of invalid or expired JWT tokens

### API Management

- Add APIs for monitoring
- View monitored APIs
- Update API configuration
- Delete monitored APIs
- Enable or disable monitoring
- Configure:
  - HTTP method
  - Expected status code
  - Check interval
  - Request timeout

### API Health Monitoring

- Automatic API health checks using Spring Scheduling
- Manual "Check Now" functionality
- Dynamic HTTP method support
- Response status code tracking
- Response time measurement
- UP/DOWN health detection
- Error message tracking

### Monitoring History

- Stores individual monitoring results
- Paginated monitoring history
- Latest checks displayed first
- Status code and response time history
- Error information for failed requests

### Statistics

- Latest API status
- Total number of checks
- Uptime percentage
- Average response time

### Frontend

- Responsive React interface
- Tailwind CSS styling
- Dashboard
- Add/Edit API forms
- Monitoring history
- API statistics
- Toast notifications
- Loading and error states

---

## Tech Stack

### Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT (JJWT)
- Spring Scheduling
- REST APIs
- Jakarta Validation
- Maven

### Frontend

- React
- Vite
- JavaScript
- React Router
- Axios
- Tailwind CSS
- React Hot Toast

### Database

- MySQL

### Development Tools

- Git
- GitHub
- Postman
- Swagger / OpenAPI
- VS Code
- MySQL Workbench

---

## Architecture

The application follows a layered architecture:

```text
React Frontend
      |
      | HTTP / JSON
      | JWT Authorization
      v
Spring Boot Controllers
      |
      v
Service Layer
      |
      v
Repository Layer
      |
      v
MySQL Database
```

The API monitoring process works independently through Spring Scheduling:

```text
Scheduler
   |
   v
Find Active APIs
   |
   v
Check Monitoring Interval
   |
   v
ApiHealthChecker
   |
   v
Send HTTP Request
   |
   +---- Expected Status ----> UP
   |
   +---- Unexpected/Error ---> DOWN
   |
   v
Save MonitoringResult
```

---

## Main Database Relationships

```text
User
 |
 | 1
 |
 | N
MonitoredApi
 |
 | 1
 |
 | N
MonitoringResult
```

### User

Represents a registered application user.

### MonitoredApi

Stores information about an API configured by a user.

### MonitoringResult

Stores the result of each API health check.

---

## Authentication Flow

```text
User Login
    |
    v
Email + Password
    |
    v
Spring Security AuthenticationManager
    |
    v
Credentials Valid
    |
    v
Generate JWT
    |
    v
React stores JWT
    |
    v
Authorization: Bearer <token>
    |
    v
JWTAuthenticationFilter
    |
    v
SecurityContext
    |
    v
Protected API Access
```

Passwords are stored using BCrypt hashing.

---

## API Endpoints

### Authentication

| Method | Endpoint         | Description           |
| ------ | ---------------- | --------------------- |
| POST   | `/auth/register` | Register a user       |
| POST   | `/auth/login`    | Login and receive JWT |

### Monitored APIs

| Method | Endpoint               | Description               |
| ------ | ---------------------- | ------------------------- |
| POST   | `/api/apis`            | Add an API                |
| GET    | `/api/apis`            | Get user's monitored APIs |
| GET    | `/api/apis/{id}`       | Get API details           |
| PUT    | `/api/apis/{id}`       | Update API                |
| DELETE | `/api/apis/{id}`       | Delete API                |
| POST   | `/api/apis/{id}/check` | Run manual health check   |

### Monitoring

| Method | Endpoint                 | Description                      |
| ------ | ------------------------ | -------------------------------- |
| GET    | `/api/apis/{id}/results` | Get paginated monitoring history |
| GET    | `/api/apis/{id}/stats`   | Get API monitoring statistics    |

### Dashboard

| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| GET    | `/api/dashboard` | Get API dashboard data |

Protected endpoints require:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## Example API Configuration

```json
{
  "name": "Example API",
  "url": "https://example.com/api",
  "httpMethod": "GET",
  "expectedStatusCode": 200,
  "checkInterval": 60,
  "timeout": 5000,
  "active": true
}
```

---

## Project Structure

```text
API-monitoring/
│
├── API-monitoring/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/sujal/API_monitoring/
│   │   │   │       ├── Config/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       ├── entity/
│   │   │   │       ├── exception/
│   │   │   │       ├── repository/
│   │   │   │       ├── Security/
│   │   │   │       └── service/
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── application.properties.example
│   │   └── test/
│   └── pom.xml
│
├── api-monitoring-frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── layouts/
│   │   ├── pages/
│   │   └── routes/
│   └── package.json
│
├── .gitignore
└── README.md
```

---

## Running the Project Locally

### Prerequisites

Install:

- Java 21+
- Maven
- MySQL
- Node.js
- npm

### 1. Clone Repository

```bash
git clone <your-repository-url>
cd API-monitoring
```

### 2. Create MySQL Database

```sql
CREATE DATABASE api_monitoring_db;
```

### 3. Configure Backend

An example configuration is available at:

```text
API-monitoring/src/main/resources/application.properties.example
```

Create your local `application.properties` and configure the required database and JWT environment variables.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/api_monitoring_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update

server.port=5023

jwt.secret=${JWT_SECRET}
```

Never commit real database credentials or JWT secrets.

### 4. Start Backend

```bash
cd API-monitoring
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend runs on:

```text
http://localhost:5023
```

### 5. Start Frontend

Open another terminal from the repository root:

```bash
cd api-monitoring-frontend
npm install
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

## Swagger API Documentation

When the backend is running, Swagger UI can be used to explore and test the REST APIs.

```text
http://localhost:5023/swagger-ui/index.html
```

JWT-protected endpoints require a valid Bearer token.

---

## Screenshots

Screenshots will be added for:

- Login
- Registration
- Dashboard
- Add API
- Edit API
- Monitoring History
- API Statistics

---

## Security

The application implements:

- JWT authentication
- Stateless Spring Security
- BCrypt password hashing
- User-specific resource authorization
- CORS configuration
- Request validation
- Protected frontend routes

Users can access only the monitored APIs associated with their own account.

---

## Future Improvements

- Email/Slack alerts when an API goes DOWN
- Monitoring charts and analytics
- Admin dashboard
- Docker support
- CI/CD pipeline
- Production deployment
- Advanced monitoring rules

---

## Author

**Sujal Saroj**  
Java & Spring Boot Developer
