# Version

## Purpose

Defines the versioning strategy for AIOS specification and framework codebase.

## Scope

Applies to all releases, tags, and artifacts.

## Design Principles

- Follow Semantic Versioning (SemVer) for code releases
- Version AIOS specification independently from framework code
- Every version must be traceable to a Git tag

---

## 1. Versioning Scheme

### Framework: `MAJOR.MINOR.PATCH[-SNAPSHOT]`

### AIOS: `AIOS-vMAJOR.MINOR`

## 2. Current Versions

| Artifact | Version | Date |
|----------|---------|------|
| AIOS Specification | v1.0.0 | 2026-07-23 |
| Framework | 0.1.0-SNAPSHOT | 2026-07-23 |

## 3. Tag Convention

| Tag Format | Example |
|------------|--------|
| `v{version}` | `v0.1.0` |
| `aios-v{version}` | `aios-v1.0.0` |

## Forbidden

- Releasing without updating the version number
- Tagging releases from non-main branches