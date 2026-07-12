# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.

## Current Status

Phase 5 Status — Kafka Integration ✅

Implemented Apache Kafka-based asynchronous event processing using the Transactional Outbox pattern with producer, consumer, and Dockerized Kafka infrastructure.

## ✅ What is implemented in Phase 5

### Kafka Infrastructure
- Dockerized Apache Kafka broker
- Kafka UI for topic and message inspection
- Spring Kafka integration

### Event Publishing
- Kafka producer implementation using `KafkaTemplate`
- Outbox Worker publishes events to Kafka
- Topic configuration externalized through application properties

### Event Consumption
- Kafka consumer using `@KafkaListener`
- End-to-end verification of event flow
- Automatic topic creation during development

### Event-Driven Flow
- Notification persisted to PostgreSQL
- Outbox event published to Kafka
- Consumer successfully receives notification events

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