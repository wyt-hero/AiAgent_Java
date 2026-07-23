package com.aiagent.common.adr;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Architecture Decision Record (ADR).
 *
 * <p>ADRs capture important architectural decisions, their context,
 * and consequences. Based on the Michael Nygard ADR format.
 *
 * <p>Example:
 * <pre>{@code
 * AdrRecord adr = AdrRecord.of("ADR-0001", "Adopt AIOS Governance Model",
 *         AdrStatus.ACCEPTED, "AI Architecture Team",
 *         List.of("TASK-0001"));
 * }</pre>
 */
public record AdrRecord(
        /** Unique ADR identifier, e.g. "ADR-0001". */
        String id,

        /** Short descriptive title. */
        String title,

        /** Current lifecycle status. */
        AdrStatus status,

        /** Date the decision was made. */
        LocalDate date,

        /** List of people or roles who made the decision. */
        String deciders,

        /** References to related tasks or nodes. */
        List<String> references
) {
    public AdrRecord {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(deciders, "deciders must not be null");

        if (id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }

        references = (references == null) ? List.of() : List.copyOf(references);
    }

    /**
     * Create an ADR with today's date.
     */
    public static AdrRecord of(String id, String title, AdrStatus status,
                                String deciders, List<String> references) {
        return new AdrRecord(id, title, status, LocalDate.now(), deciders, references);
    }

    /**
     * Create an ADR with explicit date.
     */
    public static AdrRecord of(String id, String title, AdrStatus status,
                                LocalDate date, String deciders, List<String> references) {
        return new AdrRecord(id, title, status, date, deciders, references);
    }

    /**
     * Return a copy with updated status.
     */
    public AdrRecord withStatus(AdrStatus newStatus) {
        return new AdrRecord(id, title, newStatus, date, deciders, references);
    }

    /**
     * Whether this ADR is currently active (accepted).
     */
    public boolean isActive() {
        return status == AdrStatus.ACCEPTED;
    }

    /**
     * Filename for this ADR in the decisions directory.
     */
    public String filename() {
        return "%s-%s.md".formatted(id, slugify(title));
    }

    private static String slugify(String text) {
        return text.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
