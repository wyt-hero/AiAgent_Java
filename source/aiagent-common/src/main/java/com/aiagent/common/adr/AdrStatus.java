package com.aiagent.common.adr;

/**
 * ADR lifecycle status.
 *
 * <p>Follows the standard ADR lifecycle:
 * <pre>
 * proposed → accepted → deprecated
 *                     → superseded (by another ADR)
 * </pre>
 */
public enum AdrStatus {

    /** Under consideration, not yet decided. */
    PROPOSED,

    /** Decision has been accepted and is in effect. */
    ACCEPTED,

    /** Decision is no longer recommended. */
    DEPRECATED,

    /** Decision has been replaced by a newer ADR. */
    SUPERSEDED
}
