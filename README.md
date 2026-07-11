# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.

## Current Status

Phase 4 Status — Transactional Outbox + Outbox Worker ✅

Phase 4 extends the notification workflow by implementing the Transactional Outbox Pattern, ensuring notification creation and event persistence occur atomically.

A background worker processes pending outbox events, simulating asynchronous event publishing.

✅ What is implemented in Phase 4

Transactional Outbox

* Implemented the Transactional Outbox pattern for reliable event publishing
* Notification and outbox event are persisted atomically within a single transaction

Outbox Persistence

* Added outbox_events table using Flyway migration
* Created OutboxEvent entity and Spring Data JPA repository
* Linked outbox events to notifications using aggregateId

Event Modeling

* Introduced NotificationEventPayload DTO for event data
* Automatic JSON serialization using Jackson ObjectMapper
* Added OutboxEventType and OutboxStatus enums for type-safe event management

Background Processing

* Enabled Spring Scheduling using @EnableScheduling
* Implemented OutboxWorker to poll pending events at fixed intervals
* Automatic processing of events with status NEW

Event Lifecycle

* Simulated asynchronous event publishing through the Outbox Worker
* Automatically updates processed events from NEW to PROCESSED
* Prepared the architecture for seamless Kafka integration in the next phase

Reliability

* Ensured notification creation and event persistence are rolled back together on failure using @Transactional
* Established a production-style foundation for reliable, event-driven communication

###  Database Layer
- PostgreSQL integration using Spring Boot
- JPA/Hibernate ORM setup
- Flyway migration-based schema management

### Persistence
- Notification entity mapped to database table
- UUID-based primary key generation
- Repository layer using Spring Data JPA

###  API Enhancements
- POST /notifications → persists notification in DB
- GET /notifications/{id} → fetches notification by UUID

###  Domain Modeling
- NotificationStatus enum for type-safe state management
- NotificationType enum for channel abstraction

###  Validation & Safety
- Request validation using Bean Validation (@Valid)
- Global exception handling with proper HTTP status codes
- 404 handling for missing resources

### ️ Auditing
- Automatic createdAt timestamp using @PrePersist

###  Transaction Management
- Service layer transactional boundaries using @Transactional

---
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

---

## Next Phase Goals

Transactional Outbox pattern implementation  
Event publishing foundation for Kafka integration  
Redis idempotency layer  
Retry + DLQ mechanisms  
Kafka-based async delivery system  
Observability (logs, metrics, tracing)