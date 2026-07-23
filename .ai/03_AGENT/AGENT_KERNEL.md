# Agent Kernel

## Purpose

Defines the core agent execution engine.

## Scope

The `aiagent-kernel` module.

## Design Principles

- Simplicity, Extensibility, Isolation, Observability

---

## 1. Agent Lifecycle

```mermaid
graph LR
    Created --> Initialized --> Running
    Running --> Paused --> Running
    Running --> Completed --> Archived
    Running --> Failed --> Archived
```

## 2. Execution Loop: Think-Act-Observe

```mermaid
graph TB
    Start --> Think[Build prompt, call LLM]
    Think --> Decision{Response type?}
    Decision -->|Tool Call| Act[Execute tool]
    Act --> Observe[Process result]
    Observe --> Think
    Decision -->|Final Answer| End
```

## 3. Agent Context: agentId, sessionId, userInput, variables, messages, toolResults, state, metadata

## 4. Loop Controls: maxIterations=10, timeout=30s, retryCount=3

## 5. Events: AgentStarted, AgentCompleted, AgentFailed, ToolCall, ToolResult, LLMCall, LLMResponse, Iteration

## Forbidden

- LLM-specific logic in kernel
- Blocking indefinitely
- Sharing context between executions