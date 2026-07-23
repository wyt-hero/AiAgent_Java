# Prompt

## Purpose

Defines the prompt pipeline for constructing and managing prompts.

## Scope

The `aiagent-prompt` module.

## Design Principles

- Pipeline Pattern, Template-Driven, Token-Aware, Multi-Modal

---

## 1. Pipeline: Load Template → Resolve Variables → Apply Persona → Inject Memory → Apply Token Budget → Render

## 2. Template Syntax: `{{variable}}`, `{{#if}}...{{/if}}`, `{{#each}}...{{/each}}`

## 3. Token Budget: System 20%, Memory 30%, History 30%, Response 20%

## 4. Overflow Strategy: Truncate oldest → Summarize memory → Prioritize system prompt

## 5. Storage: `prompt/system/`, `prompt/task/`, `prompt/component/`

## Forbidden

- Hardcoding prompts in Java
- Sending without token check
- Mixing system/user content