# Tool

## Purpose

Defines the tool registry for discovery, registration, and invocation.

## Scope

The `aiagent-tool` module.

## Design Principles

- Annotation-Based, Schema-Driven, Pluggable, Safe

---

## 1. Tool Annotation: `@Tool(name, description)`, `@ToolInvoke`, `@ToolParam`

## 2. Schema Generation: Automatic JSON Schema for LLM consumption

## 3. Registry: Scan → Validate → Register → Expose

## 4. Execution: Lookup → Validate Params → Permission Check → Execute → Return Result

## 5. Categories: Data, Communication, Computation, External, System

## 6. Security: Permission check, parameter validation, timeout, audit logging, rate limiting

## Forbidden

- Undocumented side effects
- Tools without timeout
- Duplicate tool names
- Execution without permission checks