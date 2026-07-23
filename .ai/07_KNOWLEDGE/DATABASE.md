# Database Knowledge Base

## Purpose
Reference for PostgreSQL and Redis patterns.

## PostgreSQL: JSONB, pgvector, partial indexes, CTEs
- HikariCP connection pool
- EXPLAIN ANALYZE for optimization

## Redis: Key pattern `aiagent:{module}:{entity}:{id}`
- Data structures: String, Hash, List, Sorted Set, Stream
- Always set TTL

## Best Practices
- Parameterized queries
- Appropriate pool sizes
- Redis pipelines for batches
- Monitor with pg_stat_statements