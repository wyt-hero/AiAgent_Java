package com.aiagent.common.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Framework-wide SLF4J log markers for structured log categorization.
 * <p>
 * Markers allow filtering and routing log events by category.
 * Example: route SECURITY logs to a separate file, or alert on FATAL.
 * <p>
 * Usage:
 * <pre>
 * log.info(LogMarkers.SECURITY, "Agent [{}] authenticated", agentId);
 * log.error(LogMarkers.FATAL, "Unrecoverable error in kernel", ex);
 * </pre>
 */
public final class LogMarkers {

    private LogMarkers() {
        // Utility class
    }

    /**
     * Security-related events (authentication, authorization, access control).
     */
    public static final Marker SECURITY = MarkerFactory.getMarker("SECURITY");

    /**
     * Audit trail events (configuration changes, data modifications).
     */
    public static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");

    /**
     * Agent lifecycle events (start, stop, state transitions).
     */
    public static final Marker AGENT = MarkerFactory.getMarker("AGENT");

    /**
     * Tool execution events (invocation, result, failure).
     */
    public static final Marker TOOL = MarkerFactory.getMarker("TOOL");

    /**
     * Workflow execution events (graph traversal, node execution).
     */
    public static final Marker WORKFLOW = MarkerFactory.getMarker("WORKFLOW");

    /**
     * Memory operations (read, write, consolidation).
     */
    public static final Marker MEMORY = MarkerFactory.getMarker("MEMORY");

    /**
     * MCP protocol events (connection, message exchange).
     */
    public static final Marker MCP = MarkerFactory.getMarker("MCP");

    /**
     * Performance-sensitive events (slow operations, timeouts).
     */
    public static final Marker PERFORMANCE = MarkerFactory.getMarker("PERFORMANCE");

    /**
     * Unrecoverable failures requiring immediate attention.
     */
    public static final Marker FATAL = MarkerFactory.getMarker("FATAL");
}
