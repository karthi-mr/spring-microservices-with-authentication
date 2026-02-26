# Spring Microservices with JWT Authentication

A production-style Spring Boot microservices architecture implementing secure JWT-based authentication using Spring Security, Spring Cloud Gateway, and Eureka Service Discovery.

---

## 🔗 Repository
https://github.com/karthi-mr/spring-microservices-with-authentication

---

## 🧩 Architecture Overview

**Auth Service** → Issues JWT  
**API Gateway** → Validates JWT & routes requests  
**Order Service** → Protected resource service  
**Discovery Server (Eureka)** → Service registry

**Gateway Routes (via service discovery):**
- `/auth/**` → `lb://auth-service`
- `/orders/**` → `lb://order-service`

### Request Flow

Client -> API Gateway -> Auth Service (login, get JWT)
Client -> API Gateway -> Order Service (send JWT)


---

## ⚙️ Tech Stack

- Java 21
- Spring Boot 4.0.3
- Spring Security 7
- Spring Cloud 2025.1.0
- Spring Cloud Gateway (Reactive/WebFlux)
- Eureka Discovery Server (Netflix Eureka)
- JWT (jjwt)
- PostgreSQL (Dockerized)
- Maven

---

## 🧱 Services & Ports (Local)

| Service | Name (Eureka) | Port |
|--------|----------------|------|
| Discovery Server | `discovery-server` | `8761`|
| API Gateway | `gateway-service` | `8080`|
| Auth Service | `auth-service` | `8081`|
| Order Service | `order-service` | `8082`|
| PostgreSQL (Docker) | - | `5433 -> 5432`|

---

## 🔐 JWT Configuration (as used in services)

- Shared issuer: `auth-service`
- Shared secret (configured in services)

> Note: values are currently committed in config for learning/demo. For production, move to environment variables / secrets manager.

---

## 🐳 PostgreSQL (Docker) for Auth Service

This repo includes a `docker-compose.yaml` that starts PostgreSQL for the Auth Service database.

### Start Postgres with Docker Compose
```bash
docker compose up -d

docker-compose.yaml (current):

POSTGRES_USER=admin

POSTGRES_PASSWORD=admin@321

POSTGRES_DB=demo_auth

exposed port: 5433:5432

Auth Service connects to:
jdbc:postgresql://localhost:5433/demo_auth

▶️ How to Run (Local)
1) Start PostgreSQL (Docker)
docker compose up -d

2) Start Eureka Discovery Server
cd discovery-server
mvn spring-boot:run

Runs at: http://localhost:8761

3) Start Auth Service
cd auth-service
mvn spring-boot:run

Runs at: http://localhost:8081

4) Start Order Service
cd order-service
mvn spring-boot:run

Runs at: http://localhost:8082

5) Start API Gateway
cd gateway-service
mvn spring-boot:run

Runs at: http://localhost:8080

✅ Testing (High-level)

Call Auth endpoints via Gateway:

http://localhost:8080/auth/**

Copy JWT from the auth response.

Call Order endpoints via Gateway with header:

Authorization: Bearer <JWT>

http://localhost:8080/orders/**

🧠 Key Concepts Demonstrated

Stateless authentication in microservices

JWT issuance (Auth service) and JWT validation (Gateway + services)

Service discovery based routing (lb://...)

Reactive Gateway (WebFlux) vs MVC microservices

Debugging Spring Security filter chain / 401 issues

📂 Project Structure
spring-microservices-with-authentication
├── auth-service
├── discovery-server
├── gateway-service
├── order-service
└── docker-compose.yaml
```

🚀 Future Enhancements (Ideas)

Refresh tokens

Role-based authorization (RBAC)

RS256 (public/private key JWT)

Full Dockerized microservice stack (all services)

Observability (logs/tracing/metrics)

👤 Author

Karthi M
GitHub: https://github.com/karthi-mr

LinkedIn: https://www.linkedin.com/in/karthi-mr

If this project helped you, consider ⭐ starring the repo!