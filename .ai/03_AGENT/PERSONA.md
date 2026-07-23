# Persona

## Purpose

Defines the agent persona system.

## Scope

The `aiagent-persona` module.

## Design Principles

- Declarative, Composable, Runtime-Switchable, Versioned

---

## 1. Structure: Identity + Behavior + Knowledge + Constraints

## 2. Format: YAML files in `persona/` directory

```yaml
id: coding-assistant
name: "Code Assistant"
identity:
  role: "Expert Java developer"
behavior:
  response_style: { format: markdown }
constraints:
  forbidden: ["Generating malicious code"]
```

## 3. Loading: Discovery → Validation → Registration → Activation

## 4. Injection: At "Apply Persona" stage in prompt pipeline

## Forbidden

- Personas in Java code
- Personas without constraints
- Switching mid-response