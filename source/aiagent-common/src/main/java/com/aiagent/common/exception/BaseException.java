package com.aiagent.common.exception;
import lombok.Getter;
import java.util.Map;
import java.util.Objects;
@Getter
public abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> context;
    protected BaseException(ErrorCode errorCode) { this(errorCode, errorCode.message(), null, null); }
    protected BaseException(ErrorCode errorCode, String message) { this(errorCode, message, null, null); }
    protected BaseException(ErrorCode errorCode, Throwable cause) { this(errorCode, errorCode.message(), cause, null); }
    protected BaseException(ErrorCode errorCode, String message, Throwable cause) { this(errorCode, message, cause, null); }
    protected BaseException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) {
        super(message, cause, true, true);
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.context = context != null ? Map.copyOf(context) : Map.of();
    }
    public int httpStatus() { return errorCode.httpStatus(); }
    @Override public String getMessage() {
        if (context.isEmpty()) return super.getMessage();
        return "%s | context=%s".formatted(super.getMessage(), context);
    }
}