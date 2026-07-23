# ADR-0001: Adopt AIOS Governance Model

> Status: accepted
>
> Date: 2026-07-23
>
> Deciders: AI Architecture Team
>
> Refs: TASK-0001, NODE-0001

---

## Context

The AiAgent-Java project needed a governance model that:

- Enables multiple AI agents (Qoder, Cursor, Claude Code, Copilot) to collaborate
- Prevents conflicting changes to architecture
- Provides clear execution order with dependency tracking
- Maintains documentation as the single source of truth

Traditional project management tools (Jira, Trello) were not designed for AI-agent-driven development.

## Decision

We adopted the **AIOS (AI Operating System)** governance model, which defines:

1. **`.ai/` directory** as the project governance root
2. **Execution Graph** (DAG) as the single source of truth for task execution
3. **Node status machine**: PLANNED → READY → RUNNING → REVIEW → TESTING → DONE → RELEASED
4. **Required Reading** rules ensuring AI reads context before coding
5. **Milestone-based** execution with explicit dependency declarations

The `.ai/` directory structure:

```
.ai/
├── 00_PROJECT/       # Project overview, execution graph, task index
├── 01_ARCHITECTURE/  # System architecture, module boundaries
├── 02_RULES/         # Coding rules, API rules, commit rules
├── 03_DECISIONS/     # Architecture Decision Records
├── 04_SPECIFICATIONS/# API, data, integration specs
└── 05_TEMPLATE/      # Code templates
```

## Consequences

### Positive

- Clear execution order prevents dependency violations
- Multiple AI agents can work without conflicts
- Documentation stays synchronized with code
- Decision history is traceable via ADRs

### Negative

- Overhead of maintaining AIOS documents for small changes
- Learning curve for new contributors unfamiliar with AIOS

### Neutral

- All changes must go through Execution Graph nodes
- AI must read Required Reading before any development

## Alternatives Considered

### Alternative A: Traditional Project Management (Jira/GitHub Projects)

- Pros: Familiar tools, rich ecosystem
- Cons: Not designed for AI-agent workflows, no built-in dependency enforcement

### Alternative B: No Governance (Free-form Development)

- Pros: Maximum flexibility
- Cons: High risk of conflicts, no traceability, architecture drift

## References

- [AIOS Specification](../00_PROJECT/PROJECT.md)
- [Execution Graph](../00_PROJECT/EXECUTION_GRAPH.md)
- [TASK_INDEX](../00_PROJECT/TASK_INDEX.md)
