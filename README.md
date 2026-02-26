# 🚀 Spring Microservices with JWT Authentication

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-7-green)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![Docker](https://img.shields.io/badge/Database-Dockerized%20Postgres-blue)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-purple)

A production-style Spring Boot microservices architecture implementing secure JWT-based authentication using Spring Security, Spring Cloud Gateway, and Eureka Service Discovery.

This project demonstrates how authentication works across distributed services in a real-world microservice setup.

---

## 🔗 GitHub Repository

https://github.com/karthi-mr/spring-microservices-with-authentication

---

## 🏗️ Architecture Overview

Auth Service → Issues JWT  
API Gateway → Validates JWT & Routes Requests  
Order Service → Protected Resource Service

```
Client → API Gateway → Auth Service (Login)
Client → API Gateway → Order Service (With JWT)
```

---

## 🧠 Tech Stack

- Java 21
- Spring Boot 4.x
- Spring Security 7
- Spring Cloud Gateway (Reactive)
- Eureka Service Discovery
- JWT (jjwt library)
- PostgreSQL (Dockerized)
- Maven
- Docker

---

## 🔐 Authentication Flow

1. User sends login request to Auth Service
2. Auth Service validates credentials
3. JWT token is generated and returned
4. Client sends JWT in Authorization header
5. API Gateway validates token
6. Request is routed to protected services (Order Service)

Header format:

```
Authorization: Bearer <your-jwt-token>
```

---

## 📦 Microservices

### 1️⃣ Eureka Server
Service registry for all microservices.

Runs on:
```
http://localhost:8761
```

---

### 2️⃣ Auth Service
- Handles authentication
- Generates JWT
- Connected to Dockerized PostgreSQL

---

### 3️⃣ API Gateway
- Reactive WebFlux gateway
- Validates JWT
- Routes requests using service discovery
- Performs load balancing using logical service names

---

### 4️⃣ Order Service
- Protected resource service
- Requires valid JWT
- Registered with Eureka

---

## 🐳 Docker Setup (PostgreSQL)

PostgreSQL is containerized for the Auth Service database.

### Run using Docker:

```
docker run --name auth-postgres \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin@321 \
  -e POSTGRES_DB=demo_auth \
  -p 5433:5432 \
  -d postgres
```

### Or using Docker Compose

```
version: '3.8'

services:
  postgres:
    image: postgres
    container_name: auth-postgres
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin@321
      POSTGRES_DB: demo_auth
    ports:
      - "5433:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

volumes:
  postgres-data:
```

---

## ▶️ How to Run the Project

### Step 1 — Start PostgreSQL (Docker)

Make sure the container is running.

---

### Step 2 — Start Eureka Server

```
cd eureka-server
mvn spring-boot:run
```

---

### Step 3 — Start Auth Service

```
cd auth-service
mvn spring-boot:run
```

---

### Step 4 — Start Order Service

```
cd order-service
mvn spring-boot:run
```

---

### Step 5 — Start API Gateway

```
cd api-gateway
mvn spring-boot:run
```

---

## 🧪 Testing Flow

1. Login via Auth Service endpoint
2. Copy generated JWT
3. Call Order Service endpoint via Gateway
4. Pass JWT in Authorization header
5. Access granted if token is valid

---

## 🎯 Key Concepts Demonstrated

- Stateless authentication in microservices
- JWT generation and validation
- Gateway-level security implementation
- Reactive vs MVC microservice architecture
- Service discovery using Eureka
- Load balancing using logical service names (`lb://`)
- Dockerized database integration
- Debugging 401 Unauthorized issues
- Spring Security filter chain understanding

---

## 📂 Project Structure

```
spring-microservices-with-authentication
│
├── eureka-server
├── auth-service
├── order-service
├── api-gateway
└── docker-compose.yml
```

---

## 🔎 Real-World Learning Outcomes

- How JWT travels across microservices
- Security configuration pitfalls in distributed systems
- Gateway vs service-level token validation
- Handling authentication errors in production-style setups

---

## 🚀 Future Enhancements

- Refresh token implementation
- Role-based authorization (RBAC)
- RS256 public/private key JWT
- Full Dockerized microservice stack
- Centralized logging
- Monitoring & tracing (Zipkin, Prometheus, Grafana)

---

## 👨‍💻 Author

**Karthi M**  
Full Stack Developer (Spring Boot + React)

- GitHub: https://github.com/karthi-mr
- LinkedIn: https://www.linkedin.com/in/karthi-mr

If this project helped you, consider ⭐ starring the repository!

---

## 📜 License

This project is for learning and demonstration purposes.