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
- Agent Kernel Batch 4: Hooks, Registry (NODE-0018~0019 / TASK-0018~0019)
  - AgentHook: Hook interface with beforeXxx/afterXxx callbacks and priority ordering
  - HookRegistry: Hook registration with sorted invocation by order
  - AgentDescriptor: Immutable agent metadata record with withActive/withMetadata
  - AgentRegistry: Thread-safe ConcurrentHashMap-based agent registry
  - Unit tests for all components
- Agent Kernel Batch 3: Execution Loop (NODE-0017 / TASK-0017)
  - DefaultAgentExecutor: Think-act-observe execution loop with max iterations and timeout
  - Unit tests for execution loop
- Agent Kernel Batch 2 (NODE-0013~0016 / TASK-0013~0016):
  - AgentResult: Execution result record with success/failure factory methods
  - AgentStep: Individual step record (THINK/ACT/OBSERVE phases)
  - AgentConfig: Agent configuration record with Builder pattern
  - AgentLifecycle: Lifecycle hooks interface with state transition validation
  - DefaultAgentLifecycle: Default logging-based lifecycle implementation
  - AgentEvent: Sealed interface for all agent events (7 event types)
  - AgentEventListener: Event listener interface with type filtering
  - EventBus: Thread-safe event distribution with CopyOnWriteArrayList
  - Unit tests for all components
- RFC-0001-C01: AIOS Runtime Specification document
- Agent Kernel Batch 1 (NODE-0011~0012 / TASK-0011~0012):
  - AgentState: Agent lifecycle state enum (IDLE, RUNNING, COMPLETED, FAILED, CANCELLED)
  - AgentContext: Immutable execution context record with Builder pattern
  - Agent: Core agent contract interface
  - AgentExecutor: Execution interface with sync/async support
  - Unit tests for all components
- ADR system (NODE-0009 / TASK-0010): ADR template, index, ADR-0001, AdrRecord, AdrStatus, AdrRegistry, tests
- Code style enforcement (NODE-0010 / TASK-0005): .editorconfig, CodeStyleValidator, naming validators, tests
- API specification (NODE-0008 / TASK-0009): API_SPECIFICATION.md, ApiResponse, PageResponse, GlobalExceptionHandler, tests
- Unified result framework (NODE-0007 / TASK-0008): Enhanced Result, CursorResult, BatchResult, tests
- Exception framework (NODE-0006 / TASK-0007): ErrorResponse, ValidationException, ExceptionAnalyzer, tests
- Logging system (NODE-0005 / TASK-0006): LogContext, LogMarkers, logback-spring.xml, tests
- Dependency management (NODE-0004 / TASK-0004): Consolidated dependencies, DEPENDENCIES.md
- Common module (NODE-0003 / TASK-0003): Exception hierarchy, ErrorCode, DTOs, utilities, tests
- Maven multi-module (NODE-0002 / TASK-0002): 11 modules
- AIOS specification (NODE-0001 / TASK-0001): AIOS framework, rules, architecture docs

---

## [0.1.0] - 2026-07-23

### Added
- Initial project creation
- AIOS v1.0.0 specification framework
- Apache License 2.0
- Maven parent POM configuration
