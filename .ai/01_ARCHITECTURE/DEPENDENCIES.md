# Dependency Management

> Document ID: AIOS-DEP-001
>
> Version: 1.0.0
>
> Status: Stable

---

# Purpose

Dependency Management 是 AiAgent-Java 的依赖中心。

所有外部库和内部模块的版本均由此文档统一管理。

子模块 **禁止** 自行声明版本号。

---

# Principles

1. **Version Centralization** — 所有版本在父 POM `<properties>` 中定义
2. **Explicit Declaration** — 所有依赖必须在 pom.xml 中显式声明，禁止传递依赖
3. **Minimal Dependencies** — 仅引入有明确价值的依赖
4. **License Compatibility** — 所有依赖必须兼容 Apache 2.0
5. **No Forbidden Libraries** — 禁止使用 Guava, Apache Commons Lang, Log4j

---

# Version Properties

父 POM `<properties>` 中定义的版本：

| Property | Version | Library |
|----------|---------|----------|
| `spring-boot.version` | 3.5.0 | Spring Boot |
| `postgresql.version` | 42.7.3 | PostgreSQL JDBC |
| `flyway.version` | 10.15.0 | Flyway |
| `mybatis-plus.version` | 3.5.7 | MyBatis-Plus |
| `redisson.version` | 3.31.0 | Redisson |
| `ollama4j.version` | 1.0.0 | Ollama4j |
| `lombok.version` | 1.18.34 | Lombok |
| `mapstruct.version` | 1.5.5.Final | MapStruct |
| `hutool.version` | 5.8.28 | Hutool |
| `junit.version` | 5.10.3 | JUnit 5 |
| `mockito.version` | 5.12.0 | Mockito |
| `testcontainers.version` | 1.19.8 | Testcontainers |
| `springdoc.version` | 2.5.0 | SpringDoc OpenAPI |

---

# Internal Module Dependencies

| Module | Dependencies |
|--------|-------------|
| aiagent-common | (leaf — no internal deps) |
| aiagent-kernel | common |
| aiagent-prompt | common |
| aiagent-memory | common |
| aiagent-tool | common |
| aiagent-workflow | kernel, common |
| aiagent-persona | common |
| aiagent-knowledge | common |
| aiagent-mcp | tool, common |
| aiagent-api | kernel, prompt, memory, tool, workflow, common |
| aiagent-boot | api, mcp, persona, knowledge (+ all transitive) |

---

# External Dependencies by Module

## aiagent-common

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| org.projectlombok | lombok | provided |
| cn.hutool | hutool-all | compile |
| org.slf4j | slf4j-api | compile |
| com.fasterxml.jackson.core | jackson-annotations | compile |

## aiagent-kernel

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-common | compile |
| org.mapstruct | mapstruct | compile |
| io.github.ollama4j | ollama4j | compile |
| org.projectlombok | lombok | provided |

## aiagent-prompt

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-common | compile |
| org.projectlombok | lombok | provided |

## aiagent-memory

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-common | compile |
| org.postgresql | postgresql | runtime |
| com.baomidou | mybatis-plus-spring-boot3-starter | compile |
| org.redisson | redisson-spring-boot-starter | compile |
| org.projectlombok | lombok | provided |

## aiagent-tool

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-common | compile |
| org.projectlombok | lombok | provided |

## aiagent-workflow

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-kernel | compile |
| com.aiagent | aiagent-common | compile |
| org.projectlombok | lombok | provided |

## aiagent-persona

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-common | compile |
| org.projectlombok | lombok | provided |

## aiagent-knowledge

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-common | compile |
| org.postgresql | postgresql | runtime |
| com.baomidou | mybatis-plus-spring-boot3-starter | compile |
| org.projectlombok | lombok | provided |

## aiagent-mcp

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-tool | compile |
| com.aiagent | aiagent-common | compile |
| org.projectlombok | lombok | provided |

## aiagent-api

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-kernel | compile |
| com.aiagent | aiagent-prompt | compile |
| com.aiagent | aiagent-memory | compile |
| com.aiagent | aiagent-tool | compile |
| com.aiagent | aiagent-workflow | compile |
| com.aiagent | aiagent-common | compile |
| org.springframework.boot | spring-boot-starter-web | compile |
| org.springdoc | springdoc-openapi-starter-webmvc-ui | compile |
| org.mapstruct | mapstruct | compile |
| org.projectlombok | lombok | provided |

## aiagent-boot

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| com.aiagent | aiagent-api | compile |
| com.aiagent | aiagent-mcp | compile |
| com.aiagent | aiagent-persona | compile |
| com.aiagent | aiagent-knowledge | compile |
| org.springframework.boot | spring-boot-starter | compile |
| org.springframework.boot | spring-boot-starter-actuator | compile |
| org.postgresql | postgresql | runtime |
| org.flywaydb | flyway-core | compile |
| org.flywaydb | flyway-database-postgresql | runtime |
| com.baomidou | mybatis-plus-spring-boot3-starter | compile |
| org.redisson | redisson-spring-boot-starter | compile |
| org.projectlombok | lombok | provided |

---

# Test Dependencies (All Modules)

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| org.junit.jupiter | junit-jupiter | test |
| org.mockito | mockito-core | test |

Additional for api/boot:

| GroupId | ArtifactId | Scope |
|---------|-----------|-------|
| org.springframework.boot | spring-boot-starter-test | test |
| org.testcontainers | testcontainers | test |
| org.testcontainers | postgresql | test |
| org.testcontainers | junit-jupiter | test |

---

# Forbidden Libraries

| Library | Reason | Alternative |
|---------|--------|-------------|
| Apache Commons Lang | Hutool provides equivalent | `cn.hutool:hutool-all` |
| Guava | Java 21 provides equivalent | Native Java |
| Log4j | Security concerns | SLF4J + Logback |
| Jackson (direct full dep) | Managed by Spring Boot | Spring Boot auto-config |

---

# Version Update Policy

| Update Type | Policy |
|-------------|--------|
| Patch | Can be applied freely |
| Minor | Require testing of affected modules |
| Major | Require architecture review + full regression |

---

# Adding a New Dependency

1. Add version property to parent POM `<properties>`
2. Add to parent POM `<dependencyManagement>`
3. Reference in child module POM **without version**
4. Update this document
5. Update DEPENDENCY_RULES.md approved library table

---

# Forbidden

- ❌ Specifying versions in child module POMs
- ❌ Using SNAPSHOT dependencies in release builds
- ❌ Adding forbidden libraries
- ❌ Relying on transitive dependencies without explicit declaration
- ❌ Creating circular dependencies between modules

---

# Future

- Automated dependency analysis in CI
- Automated license compatibility checking
- Dependency usage metrics dashboard
- Dependabot / Renovate integration
