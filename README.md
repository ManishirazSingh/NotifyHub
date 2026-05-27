# NotifyHub
Production style distributed notification system using Spring Boot microservices, Kafka, PostgreSQL, Redis and Docker Compose.

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

Currently in Phase 1 — Project Bootstrap.

---

# Modules

## api-gateway

Entry point for all client requests. Handles routing to internal services.

## notification-service

Core service responsible for processing and managing notifications.

## common-lib

Shared utilities, DTOs, and reusable components.

---

# Current State

Multi-module Maven setup

API Gateway running

Notification Service running

Independent service ports configured

Next Phase Goals

* Notification REST APIs
* Database integration (PostgreSQL)
* Event publishing (Kafka)
* Transactional Outbox pattern
* Redis idempotency layer