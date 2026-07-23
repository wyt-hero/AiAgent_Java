package com.aiagent.common.logging;

import org.slf4j.MDC;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * MDC-based log context manager.
 * <p>
 * Provides structured context injection into log messages via SLF4J MDC.
 * Supports request tracing through requestId, traceId, agentId, and sessionId.
 * <p>
 * Usage:
 * <pre>
 * LogContext.start();
 * LogContext.setAgentId("agent-001");
 * log.info("Agent started"); // includes MDC context
 * LogContext.clear();
 * </pre>
 */
public final class LogContext {

    /**
     * MDC key for unique request identifier.
     */
    public static final String KEY_REQUEST_ID = "requestId";

    /**
     * MDC key for distributed trace identifier.
     */
    public static final String KEY_TRACE_ID = "traceId";

    /**
     * MDC key for agent identifier.
     */
    public static final String KEY_AGENT_ID = "agentId";

    /**
     * MDC key for session identifier.
     */
    public static final String KEY_SESSION_ID = "sessionId";

    /**
     * MDC key for framework name.
     */
    public static final String KEY_FRAMEWORK = "framework";

    private static final String FRAMEWORK_NAME = "AiAgent";

    private LogContext() {
        // Utility class
    }

    /**
     * Initialize a new log context with a generated requestId and traceId.
     * Sets the framework name in MDC.
     *
     * @return the generated requestId
     */
    public static String start() {
        String requestId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        MDC.put(KEY_REQUEST_ID, requestId);
        MDC.put(KEY_TRACE_ID, traceId);
        MDC.put(KEY_FRAMEWORK, FRAMEWORK_NAME);
        return requestId;
    }

    /**
     * Initialize a log context with an existing requestId.
     *
     * @param requestId the request identifier to use
     * @return the requestId
     */
    public static String start(String requestId) {
        Objects.requireNonNull(requestId, "requestId must not be null");
        MDC.put(KEY_REQUEST_ID, requestId);
        MDC.put(KEY_TRACE_ID, UUID.randomUUID().toString());
        MDC.put(KEY_FRAMEWORK, FRAMEWORK_NAME);
        return requestId;
    }

    /**
     * Set the agent ID in the log context.
     *
     * @param agentId the agent identifier
     */
    public static void setAgentId(String agentId) {
        Objects.requireNonNull(agentId, "agentId must not be null");
        MDC.put(KEY_AGENT_ID, agentId);
    }

    /**
     * Set the session ID in the log context.
     *
     * @param sessionId the session identifier
     */
    public static void setSessionId(String sessionId) {
        Objects.requireNonNull(sessionId, "sessionId must not be null");
        MDC.put(KEY_SESSION_ID, sessionId);
    }

    /**
     * Set the trace ID in the log context (for distributed tracing propagation).
     *
     * @param traceId the trace identifier
     */
    public static void setTraceId(String traceId) {
        Objects.requireNonNull(traceId, "traceId must not be null");
        MDC.put(KEY_TRACE_ID, traceId);
    }

    /**
     * Get the current request ID from MDC.
     *
     * @return the current requestId, or null if not set
     */
    public static String getRequestId() {
        return MDC.get(KEY_REQUEST_ID);
    }

    /**
     * Get the current trace ID from MDC.
     *
     * @return the current traceId, or null if not set
     */
    public static String getTraceId() {
        return MDC.get(KEY_TRACE_ID);
    }

    /**
     * Get the current agent ID from MDC.
     *
     * @return the current agentId, or null if not set
     */
    public static String getAgentId() {
        return MDC.get(KEY_AGENT_ID);
    }

    /**
     * Get the current session ID from MDC.
     *
     * @return the current sessionId, or null if not set
     */
    public static String getSessionId() {
        return MDC.get(KEY_SESSION_ID);
    }

    /**
     * Get a snapshot of the current log context.
     *
     * @return unmodifiable map of current MDC context
     */
    public static Map<String, String> snapshot() {
        return Map.copyOf(MDC.getCopyOfContextMap() != null
                ? MDC.getCopyOfContextMap()
                : Map.of());
    }

    /**
     * Restore a previously captured log context snapshot.
     *
     * @param snapshot the snapshot to restore
     */
    public static void restore(Map<String, String> snapshot) {
        Objects.requireNonNull(snapshot, "snapshot must not be null");
        MDC.setContextMap(snapshot);
    }

    /**
     * Clear all log context from MDC.
     * Should be called at the end of request processing.
     */
    public static void clear() {
        MDC.clear();
    }

    /**
     * Remove only the framework-managed keys from MDC.
     * Useful when other components have added their own MDC entries.
     */
    public static void remove() {
        MDC.remove(KEY_REQUEST_ID);
        MDC.remove(KEY_TRACE_ID);
        MDC.remove(KEY_AGENT_ID);
        MDC.remove(KEY_SESSION_ID);
        MDC.remove(KEY_FRAMEWORK);
    }
}
