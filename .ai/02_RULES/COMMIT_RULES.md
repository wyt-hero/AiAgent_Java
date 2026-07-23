# Commit Rules

## Purpose

Rules for Git commits, branching, and code review.

## Scope

All Git operations.

## Design Principles

- Atomic Commits
- Descriptive Messages
- Traceability to tasks
- Clean History

---

## 1. Commit Format: `<type>(<scope>): <subject>`

Types: feat, fix, docs, style, refactor, test, chore, perf, security

## 2. Branch Strategy

| Branch | Pattern |
|--------|--------|
| Main | `main` |
| Feature | `feature/TASK-XXXX-description` |
| Bugfix | `bugfix/TASK-XXXX-description` |
| Release | `release/vX.Y.Z` |

## 3. PR Requirements

- AIOS compliance
- Tests for new code
- Documentation updated
- At least one approval
- CI passing

## 4. Merge: Squash merge for features, merge commit for releases

## Forbidden

- Direct push to main
- Force push to shared branches
- Vague commit messages
- Committing secrets