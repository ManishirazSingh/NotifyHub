# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.

## Current Status

Phase 10 Status — Docker Compose Runtime ✅

Phase 10 focused on containerizing the NotifyHub platform and enabling the complete local development environment to be started using Docker Compose.

## ✅ What is implemented in Phase 10

### Dockerized notification-service, email-service, and sms-service
### Containerized PostgreSQL, Kafka, Redis, and Kafka UI
### Configured Docker Compose to orchestrate the complete platform
### Established inter-service communication using Docker networking
### Externalized configuration using environment variables and .env
### Updated Spring Boot applications to support both local and Docker environments
### Verified all services start successfully and communicate with each other

## Notification API

Base URL for local development:

http://localhost:8081

---

### POST /notifications

Creates a notification request and persists it into PostgreSQL.

Example:
POST http://localhost:8081/notifications  
-Header "Content-Type: application/json"  
-body '{ "userId": "user-123", "type": "EMAIL", "title": "Welcome", "message": "Hello from NotifyHub" }'

Expected response:

{
"notificationId": "generated-uuid",
"status": "QUEUED",
"type": "EMAIL",
"createdAt": "timestamp"
}

---

### GET /notifications/{id}

Fetches a persisted notification from PostgreSQL.

Example:
GET http://localhost:8081/notifications/{notificationId}

Expected response:

{
"notificationId": "uuid",
"status": "QUEUED",
"type": "EMAIL",
"createdAt": "timestamp"
}

##  Database Schema (Flyway Managed)

Table: notifications

Columns:
- id (UUID / VARCHAR) — Primary Key
- user_id (VARCHAR)
- type (VARCHAR)
- title (VARCHAR)
- message (TEXT)
- status (VARCHAR stored as ENUM STRING)
- created_at (TIMESTAMP generated via @PrePersist)

Table outbox_events

Columns:
- id (UUID / VARCHAR) — Primary Key
- aggregate_id (VARCHAR)
- event_type (VARCHAR)
- payload (VARCHAR)
- status (VARCHAR stored as ENUM STRING)
- created_at (TIMESTAMP generated via @PrePersist)

---
## Current State

Multi-module Maven setup  
API Gateway running  
Notification Service running  
PostgreSQL integration complete  
Flyway schema migrations active  
Independent service ports configured
Transactional Outbox for Events implemented.
Kafka Integration Completed (Producer and Consumer)
Redis idempotency layer  
Retry + DLQ mechanisms 
Observability (logs, metrics, tracing)
Docker Compose Runtime
---

## Next Phase Goals 

Production Polish