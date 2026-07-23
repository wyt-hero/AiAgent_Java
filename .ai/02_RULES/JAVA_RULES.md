# Java Rules

## Purpose

Mandatory Java coding rules for AiAgent-Java. All Java source code must comply.

## Scope

All Java source files across all modules.

## Design Principles

- Readability First
- Consistency across the codebase
- Modern Java (Java 21)
- Compile-time safety

---

## 1. Language Level: Java 21

Required: Records, sealed classes, pattern matching, virtual threads, text blocks.

## 2. Naming Conventions

| Element | Convention | Example |
|---------|-----------|--------|
| Package | lowercase dot-separated | `com.aiagent.kernel.context` |
| Class | PascalCase noun | `AgentContext` |
| Method | camelCase verb phrase | `executeAgent()` |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |

## 3. Method Rules

- Max 5 parameters (use parameter object if more)
- Max 50 lines per method
- Never return null for collections
- `get*` methods must not have side effects

## 4. Exception Handling

- Custom exceptions extending RuntimeException per module
- Always include cause
- Never catch Exception/Throwable directly
- Use try-with-resources

## 5. Null Safety

- Use @NonNull/@Nullable annotations
- Use Optional<T> for absent returns
- Use Objects.requireNonNull() for constructor params

## 6. Concurrency

- Virtual threads for I/O-bound tasks
- java.util.concurrent for shared state
- Never use synchronized on public API methods

## 7. Logging

- SLF4J facade
- Parameterized logging
- Never log sensitive data

## Forbidden

- Raw types
- System.out.println()
- @Autowired on fields
- instanceof + cast (use pattern matching)
- Methods longer than 50 lines