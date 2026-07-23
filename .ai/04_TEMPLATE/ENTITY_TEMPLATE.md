# Entity Template

## Purpose
Standard template for database entities.

## Template
- `@Data` + `@TableName("{table_name}")`
- Required: id (BIGSERIAL), createdAt, updatedAt, deleted
- `@TableLogic` for soft delete
- `OffsetDateTime` for timestamps

## Forbidden
- Business logic in entities
- Using java.util.Date