# Directory Rules

## Purpose

Directory structure conventions for the entire project.

## Scope

All directories at project root and within modules.

## Design Principles

- Discoverability within 3 levels
- Separation of Concerns
- Scalability
- AI-Friendly names

---

## 1. Project Root Structure

```
AiAgent-Java/
├── .ai/          # AI Operating System
├── docs/         # User documentation
├── source/       # Maven multi-module source
├── database/     # SQL migrations
├── docker/       # Docker configs
├── scripts/      # Automation scripts
├── workflow/     # Agent workflow definitions
├── prompt/       # Prompt templates
├── persona/      # Agent persona definitions
├── knowledge/    # Knowledge base for RAG
├── test/         # Integration/E2E tests
```

## 2. Naming Rules

- Lowercase directory names
- Hyphenated multi-word names
- Numbered prefix for AIOS sections
- Singular directory names

## Forbidden

- Files outside defined structure
- Non-standard directory names
- Source code outside source/{module}/src/
- Directories with spaces