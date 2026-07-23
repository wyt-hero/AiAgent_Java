# Memory

## Purpose

Defines the multi-layer memory architecture.

## Scope

The `aiagent-memory` module.

## Design Principles

- Layered, Pluggable, Relevance-Based, Privacy-Aware

---

## 1. Memory Layers

| Layer | Lifetime | Storage | Purpose |
|-------|----------|---------|--------|
| Short-Term | Session | Redis | Conversation context |
| Long-Term | Permanent | Vector Store | Facts and knowledge |
| Episodic | Permanent | PostgreSQL | Session history |

## 2. STM: Redis, TTL=session+1h, max 50 messages

## 3. LTM: Vector Store, permanent, embedding-based retrieval

## 4. EM: PostgreSQL, full session recording

## 5. Consolidation: Fact extraction, deduplication, decay, archival

## 6. Retrieval: STM first → LTM second → EM third → Merge

## Forbidden

- Storing PII in memory
- Querying LTM without relevance filtering
- Sharing memory between agents