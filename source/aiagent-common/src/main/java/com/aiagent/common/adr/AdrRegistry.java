package com.aiagent.common.adr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory registry for Architecture Decision Records.
 *
 * <p>Provides CRUD operations and query methods for ADRs.
 * Thread-safe via ConcurrentHashMap.
 *
 * <p>Example:
 * <pre>{@code
 * AdrRegistry registry = new AdrRegistry();
 * registry.register(adr);
 * Optional<AdrRecord> found = registry.findById("ADR-0001");
 * List<AdrRecord> active = registry.active();
 * }</pre>
 */
public class AdrRegistry {

    private final Map<String, AdrRecord> records = new ConcurrentHashMap<>();

    /**
     * Register a new ADR.
     *
     * @param adr the ADR record to register
     * @throws IllegalArgumentException if an ADR with the same ID already exists
     */
    public void register(AdrRecord adr) {
        if (records.containsKey(adr.id())) {
            throw new IllegalArgumentException("ADR already exists: " + adr.id());
        }
        records.put(adr.id(), adr);
    }

    /**
     * Find an ADR by its ID.
     */
    public Optional<AdrRecord> findById(String id) {
        return Optional.ofNullable(records.get(id));
    }

    /**
     * Find all ADRs with the given status.
     */
    public List<AdrRecord> findByStatus(AdrStatus status) {
        return records.values().stream()
                .filter(adr -> adr.status() == status)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Get all registered ADRs, sorted by ID.
     */
    public List<AdrRecord> all() {
        List<AdrRecord> sorted = new ArrayList<>(records.values());
        sorted.sort((a, b) -> a.id().compareTo(b.id()));
        return Collections.unmodifiableList(sorted);
    }

    /**
     * Get all active (ACCEPTED) ADRs.
     */
    public List<AdrRecord> active() {
        return findByStatus(AdrStatus.ACCEPTED);
    }

    /**
     * Update the status of an existing ADR.
     *
     * @param id        the ADR ID to update
     * @param newStatus the new status
     * @return the updated ADR record
     * @throws IllegalArgumentException if the ADR does not exist
     */
    public AdrRecord updateStatus(String id, AdrStatus newStatus) {
        AdrRecord existing = records.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("ADR not found: " + id);
        }
        AdrRecord updated = existing.withStatus(newStatus);
        records.put(id, updated);
        return updated;
    }

    /**
     * Total number of ADRs in the registry.
     */
    public int size() {
        return records.size();
    }

    /**
     * Whether the registry is empty.
     */
    public boolean isEmpty() {
        return records.isEmpty();
    }
}
