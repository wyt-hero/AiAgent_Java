# Dependency Rules

## Purpose

Defines rules governing dependencies between modules and external libraries.

## Scope

Applies to all Maven dependencies across the project.

## Design Principles

- Minimal Dependencies
- Explicit Declaration
- Version Centralization in parent POM
- License Compatibility with Apache 2.0

---

## 1. Internal Module Dependencies

Dependencies flow **downward** only. `aiagent-common` is the universal leaf. No circular dependencies.

## 2. Approved External Libraries

| Library | Purpose |
|---------|--------|
| Spring Boot 3.5.x | Application framework |
| PostgreSQL JDBC | Database connectivity |
| Flyway | Database migration |
| MyBatis-Plus | ORM framework |
| Redisson | Redis client |
| Lombok | Boilerplate reduction |
| MapStruct | Object mapping |
| Hutool | Utility toolkit |
| SpringDoc OpenAPI | API documentation |
| Testcontainers | Integration testing |

## 3. Version Management

All versions in parent POM `<properties>`. Child modules must **never** specify versions.

## Forbidden

- Adding dependencies without updating this document
- Specifying versions in child module POMs
- Using SNAPSHOT dependencies in releases
- Creating circular dependencies