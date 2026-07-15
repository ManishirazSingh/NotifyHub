# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.

## Current Status

Phase 8 Status — Redis Idempotency ✅

The notification platform now supports resilient event processing through automatic retries and Dead Letter Queue integration. Temporary failures are retried using Spring Kafka's error handling, while unrecoverable events are safely routed to a dedicated DLT for future analysis or replay. The next phase focuses on observability through health checks, metrics, and application monitoring.

## ✅ What is implemented in Phase 8

### Retry Mechanism
-- Configured Spring Kafka DefaultErrorHandler for automatic retry handling
-- Implemented fixed backoff retry policy with configurable retry attempts
-- Prevented immediate message loss caused by transient processing failures
### Dead Letter Queue (DLQ)
-- Integrated DeadLetterPublishingRecoverer
-- Configured failed events to be published to the notification-events-dlt topic after retry exhaustion
-- Preserved failed notification events for later inspection and replay
### Failure Handling
-- Automatic retry for temporary processing failures
-- Reliable handling of unrecoverable events through Dead Letter Topic
-- Verified end-to-end retry and DLQ behavior using Kafka UI
### Production Reliability
-- Implemented resilient Kafka consumer error handling
-- Separated transient failures from permanent failures
-- Improved overall reliability of asynchronous notification processing

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
---

## Next Phase Goals 

Observability (logs, metrics, tracing)