# NotifyHub Project Specification

## 1. Overview

NotifyHub is a distributed notification processing platform designed for reliable asynchronous event-driven communication.

The platform supports notification delivery workflows across multiple channels including:

* Email
* SMS
* Push Notifications

The system is designed around scalable backend patterns such as:

* asynchronous messaging
* transactional event publishing
* idempotent consumers
* retry handling
* dead-letter queue processing

NotifyHub uses Kafka as the messaging backbone and follows production-style architectural patterns to ensure reliable event processing and service decoupling.

⸻

## 2. Technology Stack

Area	Technology
Language	Java 17
Framework	Spring Boot
API Gateway	Spring Cloud Gateway
Messaging	Apache Kafka
Database	PostgreSQL
Cache	Redis
Build Tool	Maven
Containerization	Docker Compose

⸻

## 3. High-Level Architecture

System Architecture Diagram

                            +------------------+
                            |      Client      |
                            +------------------+
                                      |
                                      v
                         +------------------------+
                         |      API Gateway       |
                         | Spring Cloud Gateway   |
                         +------------------------+
                                      |
                                      v
                    +-----------------------------------+
                    |     Notification Service          |
                    |-----------------------------------|
                    | - Request Validation              |
                    | - Notification Persistence        |
                    | - Outbox Event Creation           |
                    +-----------------------------------+
                           |                 |
                           |                 |
                           v                 v
               +----------------+    +------------------+
               | PostgreSQL DB  |    |  Outbox Table   |
               +----------------+    +------------------+
                                              |
                                              v
                              +-----------------------------+
                              |        Outbox Worker        |
                              |-----------------------------|
                              | Polls unpublished events    |
                              | Publishes events to Kafka   |
                              +-----------------------------+
                                              |
                                              v
                              +-----------------------------+
                              |          Kafka              |
                              +-----------------------------+
                                  |        |         |
                    --------------         |         --------------
                    |                      |                      |
                    v                      v                      v
        +------------------+   +------------------+   +------------------+
        | Email Consumer   |   | SMS Consumer     |   | Push Consumer    |
        +------------------+   +------------------+   +------------------+
                    |                      |                      |
                    ----------------------------------------------
                                              |
                                              v
                              +-----------------------------+
                              |            Redis            |
                              |-----------------------------|
                              | - Idempotency Tracking      |
                              | - Duplicate Prevention      |
                              | - Rate Limiting             |
                              +-----------------------------+
                                              |
                                              v
                              +-----------------------------+
                              |        Retry / DLQ          |
                              +-----------------------------+

⸻

## 4. Core Components

4.1 API Gateway

The API Gateway acts as the centralized entry point for all external requests.

Responsibilities:

* request routing
* centralized request handling
* future authentication integration
* request tracing and logging

Technology:

* Spring Cloud Gateway

⸻

4.2 Notification Service

The Notification Service is responsible for:

* accepting notification requests
* validating payloads
* persisting notification data
* generating outbox events

The service acts as the primary write component of the platform.

⸻

4.3 PostgreSQL

PostgreSQL is used for:

* notification persistence
* transactional outbox storage
* reliable transactional consistency

⸻

4.4 Transactional Outbox

The transactional outbox pattern is used to ensure consistency between:

* database transactions
* Kafka event publishing

Instead of publishing Kafka events directly within application transactions:

1. notification data is persisted
2. outbox event is stored within the same transaction
3. a separate worker publishes events asynchronously

This approach reduces the risk of dual-write inconsistencies.

⸻

4.5 Outbox Worker

Responsibilities:

* polling unpublished outbox events
* publishing events to Kafka
* updating event publishing status

The worker decouples transactional persistence from asynchronous event publishing.

⸻

4.6 Kafka

Kafka acts as the asynchronous messaging backbone of the platform.

Responsibilities:

* event distribution
* service decoupling
* asynchronous communication
* retry support
* scalable event consumption

Planned topics:

* notification.email
* notification.sms
* notification.push
* notification.dlq

⸻

4.7 Consumer Services

Dedicated consumers process notification events independently for each delivery channel.

Planned consumers:

* Email Consumer
* SMS Consumer
* Push Consumer

Responsibilities:

* consume Kafka events
* process notification delivery
* support retries
* maintain idempotent processing

⸻

4.8 Redis

Redis is used for:

* idempotency tracking
* duplicate prevention
* lightweight rate limiting
* temporary fast-access state management

⸻

## 5. Notification Processing Flow

Step 1 — Request Submission

Client submits notification request through API Gateway.

⸻

Step 2 — Request Routing

Gateway routes request to Notification Service.

⸻

Step 3 — Transactional Persistence

Notification Service:

* validates request
* persists notification record
* stores outbox event in the same transaction

⸻

Step 4 — Outbox Polling

Outbox Worker polls unpublished events from the outbox table.

⸻

Step 5 — Kafka Publishing

Worker publishes events to Kafka topics.

⸻

Step 6 — Event Consumption

Relevant consumer receives notification event.

Examples:

* Email Consumer
* SMS Consumer
* Push Consumer

⸻

Step 7 — Idempotency Validation

Redis validates whether the event has already been processed.

⸻

Step 8 — Notification Delivery

Consumer processes notification delivery workflow.

⸻

Step 9 — Failure Handling

Failed events trigger:

* retry processing
* dead-letter queue publishing
* failure logging

⸻

## 6. Reliability Patterns

Transactional Outbox

Prevents inconsistency between:

* database commits
* Kafka publishing

⸻

Retry Handling

Supports automatic retry for transient failures.

Examples:

* downstream timeout
* temporary network issue
* provider unavailability

⸻

Dead Letter Queue (DLQ)

Repeatedly failing events are redirected to DLQ topics for operational analysis.

⸻

Idempotent Consumers

Consumers safely handle duplicate event delivery caused by:

* retries
* offset replay
* consumer restarts

⸻

## 7. Planned Development Phases

Phase	 Description
Phase 0	 Project Foundation
Phase 1	 Multi-Module Maven Bootstrap
Phase 2	 Notification REST API
Phase 3	 Persistence Layer
Phase 4	 Transactional Outbox
Phase 5	 Kafka Integration
Phase 6	 Consumer Services
Phase 7	 Redis Idempotency
Phase 8	 Retry + DLQ
Phase 9	 Observability
Phase 10 Docker Compose Runtime
Phase 11 Production Polish

⸻

## 8. Engineering Principles

* Incremental architectural evolution
* Reliable asynchronous processing
* Clear service boundaries
* Maintainable backend design
* Production-style engineering standards
* Minimal unnecessary abstraction

⸻

## 9. Future Enhancements

Potential future enhancements:

* authentication and authorization
* distributed tracing
* metrics dashboards
* scheduled notifications
* provider integrations
* advanced rate limiting
* Kafka partition scaling strategies