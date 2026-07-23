# Database Rules

## Purpose

Rules for database design, migration, and access patterns.

## Scope

All database-related code including SQL, entities, repositories.

## Design Principles

- Migration-First (Flyway only)
- Convention Over Configuration
- Separation of database logic
- Performance from the start

---

## 1. Technology: PostgreSQL 16, Redis 7, Flyway 10.x, MyBatis-Plus 3.5.x

## 2. Naming: snake_case tables/columns, `id` BIGSERIAL PK, `idx_`/`uk_` prefixes

## 3. Required Columns: id, created_at, updated_at, deleted (soft delete)

## 4. Migration Naming: `V{timestamp}__{description}.sql`

## 5. Query Rules

- Never use SELECT *
- Always filter by deleted = FALSE
- Use pagination for list queries
- Parameterized queries only

## 6. Redis Key Pattern: `aiagent:{module}:{entity}:{id}`

## Forbidden

- Manual DDL outside Flyway
- SELECT * in any query
- Storing secrets in database
- Tables without required columns