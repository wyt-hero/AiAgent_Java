# API Rules

## Purpose

Rules for designing, implementing, and documenting APIs.

## Scope

All REST endpoints, WebSocket handlers, MCP interfaces.

## Design Principles

- Consistency
- Discoverability via OpenAPI
- Robustness
- URL versioning

---

## 1. REST: `/api/v1/{resources}`, plural nouns, standard HTTP methods

## 2. Response Envelope

```json
{"code": 200, "message": "success", "data": {}, "timestamp": "...", "traceId": "..."}
```

## 3. Status Codes: 200/201/204 success, 400/401/403/404/429/500 errors

## 4. Pagination: `?page=1&size=20&sort=createdAt,desc`

## 5. Error Codes: 40000-40099 auth, 40200-40299 validation, 40300-40399 resource, 50000+ internal

## Forbidden

- Exposing entities directly
- GET for state changes
- Stack traces in responses
- Endpoints without OpenAPI docs