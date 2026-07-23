package com.aiagent.common.exception;

/**
 * Error code contract for the entire framework.
 * Every module must define its own error codes implementing this interface.
 */
public interface ErrorCode {

    /**
     * Numeric error code.
     */
    int code();

    /**
     * Human-readable error name (used in API responses).
     */
    default String name() {
        return getClass().getSimpleName();
    }

    /**
     * Human-readable error message template.
     */
    String message();

    /**
     * HTTP status code associated with this error.
     */
    default int httpStatus() {
        return 500;
    }
}
