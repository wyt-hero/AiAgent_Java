# Service Template

## Purpose
Standard template for service classes.

## Scope
All service classes across modules.

## Template
- Interface + Impl pattern
- `@Service` + `@RequiredArgsConstructor`
- `@Transactional` for writes
- Throw domain exceptions
- Return DTOs, never entities

## Forbidden
- HTTP annotations in services
- Direct SQL
- Returning entities