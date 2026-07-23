package com.aiagent.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * Standardized error response for all API endpoints.
 * Provides a consistent error format across the entire framework.
 *
 * <p>Example JSON output:
 * <pre>{@code
 * {
 *   "code": 30001,
 *   "error": "AGENT_EXECUTION_FAILED",
 *   "message": "Agent execution failed: timeout",
 *   "path": "/api/v1/agents/execute",
 *   "timestamp": "2026-07-23T10:30:00Z",
 *   "traceId": "abc-123-def",
 *   "details": { "retryAfter": 5 }
 * }
 * }</pre>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int code,
        String error,
        String message,
        String path,
        OffsetDateTime timestamp,
        String traceId,
        Map<String, Object> details
) {

    public ErrorResponse {
        Objects.requireNonNull(error, "error must not be null");
        Objects.requireNonNull(message, "message must not be null");
        timestamp = timestamp != null ? timestamp : OffsetDateTime.now();
        details = details != null ? Map.copyOf(details) : null;
    }

    /**
     * Create an error response from a BaseException.
     */
    public static ErrorResponse from(BaseException exception) {
        return from(exception, null, null);
    }

    /**
     * Create an error response from a BaseException with path info.
     */
    public static ErrorResponse from(BaseException exception, String path, String traceId) {
        return new ErrorResponse(
                exception.getErrorCode().code(),
                exception.getErrorCode().name(),
                exception.getMessage(),
                path,
                OffsetDateTime.now(),
                traceId,
                exception.getContext().isEmpty() ? null : exception.getContext()
        );
    }

    /**
     * Create an error response from an ErrorCode.
     */
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.code(),
                errorCode.name(),
                errorCode.message(),
                null,
                OffsetDateTime.now(),
                null,
                null
        );
    }

    /**
     * Create an error response from an ErrorCode with custom message.
     */
    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return new ErrorResponse(
                errorCode.code(),
                errorCode.name(),
                message,
                path,
                OffsetDateTime.now(),
                null,
                null
        );
    }

    /**
     * Create an error response with all fields.
     */
    public static ErrorResponse of(int code, String error, String message, String path,
                                   String traceId, Map<String, Object> details) {
        return new ErrorResponse(code, error, message, path, OffsetDateTime.now(), traceId, details);
    }
}
