# NotifyHub

NotifyHub is a distributed notification processing platform being built using production-style backend engineering patterns with Java and Spring Boot.

The architecture, module boundaries, and implementation roadmap are defined in docs/PROJECT_SPEC.md.
## Planned Features

- API Gateway

- Notification Service

- Kafka-based async delivery

- Transactional Outbox Pattern

- Retry + DLQ

- Redis Idempotency

- Observability

- Dockerized Local Runtime

## Current Status

Phase 2 Status — Notification API ✅

Phase 2 introduces the first working slice of notification-service.

Implemented in this phase:

* Java 17 multi-module Maven project structure
* API Gateway service bootstrap
* Notification Service bootstrap
* Notification REST API implementation
* Request/response DTO structure
* Service layer abstraction
* Notification type enum support
* Notification request processing flow





### Current modules:

* common-lib
* api-gateway
* notification-service

### Planned future modules:

* outbox-worker
* email-service
* sms-service
* push-service

⸻

## Notification API

Base URL for local development:

http://localhost:8081

POST /notifications

Creates a notification request and returns a generated notification identifier.

### Example:

 POST http://localhost:8081/notifications \
-H "Content-Type: application/json" \
-d '{
"userId": "user-123",
"type": "EMAIL",
"title": "Welcome",
"message": "Hello from NotifyHub"
}'

Expected response:

{
"notificationId": "generated-uuid",
"status": "QUEUED"
}
