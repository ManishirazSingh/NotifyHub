# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.

## Current Status

Phase 9 Status — Observability ✅

The platform now provides production-style observability through Spring Boot Actuator. Each microservice exposes health, information, and metrics endpoints, enabling runtime monitoring and diagnostics. Structured logging improves traceability of notification processing, making it easier to monitor, debug, and troubleshoot distributed workflows.

## ✅ What is implemented in Phase 9

### Spring Boot Actuator
-- Added Spring Boot Actuator to all microservices
-- Exposed production-ready monitoring endpoints
-- Configured application health and information endpoints
### Health Monitoring
-- Enabled /actuator/health for service health checks
-- Configured detailed health information for runtime diagnostics
-- Integrated database health monitoring through Spring Boot
### Application Information
-- Exposed application metadata using /actuator/info
-- Added service name, version, and description for easier identification
### Metrics
-- Enabled /actuator/metrics for runtime metrics collection
-- Prepared the services for integration with monitoring platforms such as Prometheus and Grafana

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
---

## Next Phase Goals 

Docker Compose Runtime