# Module Architecture

## Purpose

Defines the Maven multi-module structure of AiAgent-Java.

## Scope

Applies to all Maven modules within `source/`.

## Design Principles

- Single Responsibility per module
- Explicit Dependencies
- API Separation from implementation
- Testability in isolation

---

## 1. Module Structure

```mermaid
graph TB
    Parent[aiagent-java]
    Parent --> Common[aiagent-common]
    Parent --> Kernel[aiagent-kernel]
    Parent --> Prompt[aiagent-prompt]
    Parent --> Memory[aiagent-memory]
    Parent --> Tool[aiagent-tool]
    Parent --> Workflow[aiagent-workflow]
    Parent --> Persona[aiagent-persona]
    Parent --> Knowledge[aiagent-knowledge]
    Parent --> MCP[aiagent-mcp]
    Parent --> API[aiagent-api]
    Parent --> Boot[aiagent-boot]

    Kernel --> Common
    Prompt --> Common
    Memory --> Common
    Tool --> Common
    Workflow --> Kernel
    MCP --> Tool
    API --> Kernel
    API --> Prompt
    API --> Memory
    API --> Tool
    API --> Workflow
    Boot --> API
    Boot --> MCP
```

## 2. Module Definitions

| Module | Responsibility | Depends On |
|--------|---------------|------------|
| aiagent-common | Shared utilities, base types | Nothing |
| aiagent-kernel | Core agent execution engine | common |
| aiagent-prompt | Prompt construction pipeline | common |
| aiagent-memory | Multi-layer memory system | common |
| aiagent-tool | Tool registration and invocation | common |
| aiagent-workflow | Graph-based workflow orchestration | kernel, common |
| aiagent-persona | Agent persona management | common |
| aiagent-knowledge | Knowledge base and RAG | common |
| aiagent-mcp | MCP integration | tool, common |
| aiagent-api | REST and WebSocket APIs | kernel, prompt, memory, tool, workflow |
| aiagent-boot | Spring Boot auto-configuration | All modules |

## Forbidden

- Circular dependencies between modules
- Modules depending on aiagent-boot
- Sharing implementation classes between modules