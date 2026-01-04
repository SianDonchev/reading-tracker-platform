# Reading Tracker Backend

Java backend project demonstrating:
- event-driven architecture with Kafka
- CQRS-style projections
- asynchronous processing
- clean domain modeling

## High-level idea
Users track reading progress per book.
When someone finishes a book, others who finished it earlier are notified asynchronously.

## Architecture
- reading-service (write model)
- ranking-service (projection)
- notification-service (projection + side effects)

## Tech stack
- Java 21
- Spring Boot
- Kafka
- PostgreSQL
- Docker Compose
