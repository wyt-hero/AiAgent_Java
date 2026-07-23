package com.aiagent.common.exception;

import java.util.Map;

/**
 * Exception thrown during memory operations.
 */
public class MemoryException extends BaseException {

    public MemoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemoryException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public MemoryException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public MemoryException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public MemoryException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) {
        super(errorCode, message, cause, context);
    }

    public static MemoryException readFailed(String detail, Throwable cause) {
        return new MemoryException(
                CommonErrorCode.MEMORY_READ_FAILED,
                CommonErrorCode.MEMORY_READ_FAILED.message().formatted(detail),
                cause
        );
    }

    public static MemoryException writeFailed(String detail, Throwable cause) {
        return new MemoryException(
                CommonErrorCode.MEMORY_WRITE_FAILED,
                CommonErrorCode.MEMORY_WRITE_FAILED.message().formatted(detail),
                cause
        );
    }

    public static MemoryException consolidationFailed(String detail, Throwable cause) {
        return new MemoryException(
                CommonErrorCode.MEMORY_CONSOLIDATION_FAILED,
                CommonErrorCode.MEMORY_CONSOLIDATION_FAILED.message().formatted(detail),
                cause
        );
    }
}
