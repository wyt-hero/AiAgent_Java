# MCP

## Purpose

Defines MCP integration for standardized agent-tool communication.

## Scope

The `aiagent-mcp` module.

## Design Principles

- Protocol Compliance, Dual Mode (server+client), Security, Discoverability

---

## 1. Architecture: Agent ↔ MCP Client ↔ External Tools, External Agent ↔ MCP Server ↔ Agent

## 2. Server: Expose tools, resources, prompts via HTTP+SSE or stdio

## 3. Client: Connect → Initialize → Discover → Invoke → Monitor

## 4. Tool Bridge: Internal Tool Registry ↔ MCP Bridge ↔ Remote MCP Tools

## 5. Security: Bearer token auth, tool-level ACL, TLS, rate limiting, audit logging

## Forbidden

- Unauthenticated MCP connections
- Exposing tools without access control
- Blocking agent thread on MCP calls