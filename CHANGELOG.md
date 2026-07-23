# Changelog

## Purpose

This document records all notable changes to AiAgent-Java in chronological order.

## Scope

Covers all releases, milestones, and significant changes from project inception.

## Design Principles

- Follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) format
- Each entry includes date, version, and categorized changes
- Changes are grouped by type: Added, Changed, Deprecated, Removed, Fixed, Security

---

## [Unreleased]

### Added
- Logging system (NODE-0005 / TASK-0006)
  - LogContext: MDC-based structured logging context manager (requestId, traceId, agentId, sessionId)
  - LogMarkers: SLF4J markers for log categorization (SECURITY, AUDIT, AGENT, TOOL, WORKFLOW, MEMORY, MCP, PERFORMANCE, FATAL)
  - logback-spring.xml: Default logging configuration with structured format, rolling files, security/audit separation
  - Unit tests for LogContext and LogMarkers
- Dependency management consolidation (NODE-0004 / TASK-0004)
  - Added jackson-annotations, ollama4j to parent POM dependencyManagement
  - Added PostgreSQL + MyBatis-Plus + Redisson to aiagent-memory module
  - Added PostgreSQL + MyBatis-Plus to aiagent-knowledge module
  - Added ollama4j to aiagent-kernel module
  - Created DEPENDENCIES.md as dependency center document
- Common module (aiagent-common) implementation (NODE-0003 / TASK-0003)
  - Exception hierarchy: ErrorCode, BaseException, AgentException, ToolException, PromptException, MemoryException, WorkflowException, McpException, KnowledgeException
  - CommonErrorCode enum with 30+ error codes across 9 categories
  - Unified response DTOs: Result<T>, PageResult<T>, PageRequest
  - Annotations: @NonNull, @Nullable
  - Utilities: StringUtils, CollectionUtils
  - Constants: FrameworkConstants
  - Unit tests for all components
- Maven multi-module architecture with 11 modules (NODE-0002 / TASK-0002)
  - aiagent-common: Shared utilities and base types
  - aiagent-kernel: Core agent execution engine
  - aiagent-prompt: Prompt construction and pipeline
  - aiagent-memory: Multi-layer memory system
  - aiagent-tool: Tool registration and invocation
  - aiagent-workflow: Graph-based workflow orchestration
  - aiagent-persona: Agent persona management
  - aiagent-knowledge: Knowledge base and RAG pipeline
  - aiagent-mcp: Model Context Protocol integration
  - aiagent-api: REST and WebSocket API surface
  - aiagent-boot: Spring Boot entry point
- Internal module dependency management in parent POM
- TASK_INDEX as project task center
- EXECUTION_GRAPH as single source of truth for project execution

---

## [0.1.0] - 2026-07-23

### Added
- Initial project creation
- Repository setup
- AIOS v1.0.0 specification framework
- Project structure initialization (TASK-0001)
- AI Operating System (AIOS) specification (TASK-0001)
- Apache License 2.0
- Maven parent POM configuration
- Docker Compose development environment skeleton
- Development rules and conventions documentation

---

## Format Guide

Each release entry follows this structure:

```markdown
## [version] - YYYY-MM-DD

### Added
- New features

### Changed
- Changes to existing functionality

### Deprecated
- Soon-to-be removed features

### Removed
- Removed features

### Fixed
- Bug fixes

### Security
- Vulnerability fixes
```

## Best Practices

- Update CHANGELOG.md with every pull request
- Reference task IDs (e.g., TASK-0001) in entries
- Keep entries concise but informative

## Forbidden

- Accumulating changes without updating the changelog
- Writing vague entries like "misc fixes"
- Removing or altering historical entries
