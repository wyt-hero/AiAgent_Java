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
