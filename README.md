# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.

## Current Status

Phase 6 Status — Redis Idempotency ✅

The platform ensures reliable event processing by preventing duplicate notification handling across Email and SMS consumer services. Redis is used to track processed notification events, enabling idempotent consumers capable of safely handling repeated Kafka message deliveries. The next phase introduces Retry mechanisms and Dead Letter Queues (DLQ) for resilient failure handling.

## ✅ What is implemented in Phase 6

### Redis Integration
- Integrated Redis as an in-memory datastore for idempotency
- Configured Spring Data Redis for consumer services
- Docker Compose updated to include Redis

### Idempotent Event Processing
- Implemented IdempotencyService to detect duplicate Kafka events
- Processed notification IDs stored in Redis with automatic expiration (TTL)
- Duplicate events are identified and skipped before business processing

### Consumer Reliability
- Email Service processes each notification only once
- SMS Service processes each notification only once
- Duplicate Kafka message deliveries are safely ignored

### Distributed Systems Pattern
- Implemented idempotent consumer pattern for event-driven architecture
- Prevented duplicate notification delivery caused by Kafka message reprocessing
- Centralized idempotency logic for clean and reusable design

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
---

## Next Phase Goals

Retry + DLQ mechanisms  
Kafka-based async delivery system  
Observability (logs, metrics, tracing)