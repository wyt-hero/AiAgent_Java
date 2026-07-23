# SQL Template

## Purpose
Standard template for Flyway migrations.

## Template
- Keywords UPPERCASE
- Required columns: id, created_at, updated_at, deleted
- Always add COMMENT ON TABLE/COLUMN
- Create indexes for queried columns

## Forbidden
- SELECT *
- Dropping tables without backup
- Modifying applied migrations