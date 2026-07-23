# Workflow

## Purpose

Defines the graph-based workflow engine.

## Scope

The `aiagent-workflow` module.

## Design Principles

- Graph-Based (DAGs), Declarative, Stateful, Composable

---

## 1. Concepts: Node, Edge, State, Context

## 2. Node Types: Agent, Tool, Condition, Parallel, Wait, Sub-workflow

## 3. Definition: YAML in `workflow/` directory

## 4. Execution: Load State → Execute Node → Update State → Evaluate Edges → Next/End/Pause

## 5. State: Persisted after each node, accessible via `${state.*}`

## 6. Error Handling: Retry, Skip, Fallback, Abort

## Forbidden

- Uncontrolled cycles
- Mutable state between parallel branches
- Workflows in Java code