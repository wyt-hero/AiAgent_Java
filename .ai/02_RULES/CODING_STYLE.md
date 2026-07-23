# Coding Style

## Purpose

Unified coding style for all source code and configuration files.

## Scope

Java, XML, YAML, JSON, SQL, properties, shell scripts.

## Design Principles

- Consistency
- Readability
- Automation through tooling

---

## 1. General Formatting

- 4 spaces indent (no tabs)
- Max 120 char line length
- LF line ending
- UTF-8 encoding
- Trailing whitespace removed
- Final newline required

## 2. Java: Opening brace on same line, no wildcard imports, annotations on separate line

## 3. SQL: Keywords UPPERCASE, one column per line in CREATE TABLE

## 4. Comments: Javadoc for all public APIs, English only, no commented code, TODO format: `// TODO [TASK-XXXX]`

## Forbidden

- Mixing tabs and spaces
- Lines > 120 chars
- Commented-out code
- Non-ASCII in Java identifiers