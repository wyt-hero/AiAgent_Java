# Controller Template

## Purpose
Standard template for REST controllers.

## Scope
All controllers in `aiagent-api`.

## Template
- `@RestController` + `@RequestMapping("/api/v1/{resources}")`
- Standard CRUD: GET/{id}, GET, POST, PUT/{id}, DELETE/{id}
- Always use `ApiResponse<T>` envelope
- Full OpenAPI annotations

## Forbidden
- Business logic in controllers
- Direct entity returns
- Missing @Operation annotations