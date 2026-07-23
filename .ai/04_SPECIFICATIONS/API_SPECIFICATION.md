# API Specification

## Purpose

定义 AiAgent-Java 框架的统一 API 规范。

所有 REST 端点、WebSocket 接口必须遵循此规范。

## Scope

适用于 `aiagent-api` 模块暴露的所有外部 API。

## Design Principles

- **一致性** — 所有 API 遵循相同的响应格式和错误处理模式
- **可发现性** — 通过 OpenAPI 3 注解实现自文档化
- **健壮性** — 宽进严出（Accept broadly, return strictly）
- **版本控制** — URL 路径版本号 `/api/v{N}/`

---

## 1. Response Envelope

### 1.1 Success Response

所有成功响应必须使用 `ApiResponse<T>` 包装：

```json
{
    "success": true,
    "code": 0,
    "message": "success",
    "data": { },
    "timestamp": "2026-07-23T10:00:00Z"
}
```

### 1.2 Error Response

所有错误响应必须使用 `ErrorResponse` 格式：

```json
{
    "code": 30002,
    "error": "AGENT_NOT_FOUND",
    "message": "Agent not found: agent-001",
    "path": "/api/v1/agents/agent-001",
    "timestamp": "2026-07-23T10:00:00Z",
    "traceId": "abc-123-def",
    "details": { "agentId": "agent-001" }
}
```

### 1.3 Paginated Response

分页列表响应：

```json
{
    "success": true,
    "code": 0,
    "message": "success",
    "data": {
        "content": [],
        "page": 1,
        "size": 20,
        "totalElements": 100,
        "totalPages": 5,
        "hasNext": true,
        "hasPrevious": false
    },
    "timestamp": "2026-07-23T10:00:00Z"
}
```

---

## 2. Exception Mapping

`GlobalExceptionHandler` 负责将框架异常映射为标准 HTTP 响应：

| Exception | HTTP Status | Error Code Range |
|-----------|------------|------------------|
| `ValidationException` | 400 Bad Request | 10002 |
| `AuthenticationException` (future) | 401 Unauthorized | 20001 |
| `AuthorizationException` (future) | 403 Forbidden | 20002 |
| `AgentException.notFound()` | 404 Not Found | 30002 |
| `ToolException.notFound()` | 404 Not Found | 40001 |
| `ConflictException` (future) | 409 Conflict | 10009 |
| `RateLimitException` (future) | 429 Too Many Requests | 10004 |
| `AgentException.timeout()` | 504 Gateway Timeout | 30003 |
| `BaseException` (fallback) | 500 Internal Server Error | varies |
| `Exception` (unknown) | 500 Internal Server Error | 10001 |

---

## 3. URL Conventions

| Pattern | Example | Description |
|---------|---------|-------------|
| Collection | `/api/v1/agents` | List/create |
| Single resource | `/api/v1/agents/{id}` | Get/update/delete |
| Sub-resource | `/api/v1/agents/{id}/sessions` | Nested resource |
| Action | `/api/v1/agents/{id}/execute` | Execute action |

---

## 4. HTTP Methods

| Method | Usage | Idempotent | Success Response |
|--------|-------|-----------|------------------|
| GET | Retrieve | Yes | 200 |
| POST | Create | No | 201 |
| PUT | Full update | Yes | 200 |
| PATCH | Partial update | Yes | 200 |
| DELETE | Remove | Yes | 204 |

---

## 5. Pagination

所有列表端点必须支持分页：

```
GET /api/v1/agents?page=1&size=20&sort=createdAt,desc
```

参数：
- `page` — 页码（从 1 开始）
- `size` — 每页大小（默认 20，最大 100）
- `sort` — 排序字段和方向

---

## 6. OpenAPI Documentation

- 所有端点必须使用 `@Operation` 注解
- 所有参数必须使用 `@Parameter` 注解
- 所有 DTO 字段必须使用 `@Schema` 注解
- 每个 Controller 必须使用 `@Tag` 注解

---

## 7. Error Code Ranges

| Range | Category |
|-------|----------|
| 10000-19999 | General errors |
| 20000-29999 | Authentication/Authorization |
| 30000-39999 | Agent errors |
| 40000-49999 | Tool errors |
| 50000-59999 | Prompt errors |
| 60000-69999 | Memory errors |
| 70000-79999 | Workflow errors |
| 80000-89999 | MCP errors |
| 90000-99999 | Knowledge errors |

---

## Best Practices

- 始终使用 `ApiResponse.ok()` 包装成功响应
- 始终使用 `GlobalExceptionHandler` 处理异常
- 不要在 Controller 中捕获异常
- 不要在 API 响应中暴露数据库实体
- 所有时间字段使用 `OffsetDateTime`

## Forbidden

- 直接返回实体对象
- 在错误响应中暴露堆栈跟踪
- 在 Controller 中编写业务逻辑
- 创建没有 OpenAPI 文档的端点
