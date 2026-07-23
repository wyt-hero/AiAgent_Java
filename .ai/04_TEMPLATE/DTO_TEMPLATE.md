# DTO Template

## Purpose
Standard template for DTOs.

## Template
- Use `record` for all DTOs
- Response DTO: `@Schema` on each field
- Create Request: validation annotations
- Update Request: all fields optional

## Forbidden
- Mutable DTO classes
- Missing @Schema
- Exposing entities