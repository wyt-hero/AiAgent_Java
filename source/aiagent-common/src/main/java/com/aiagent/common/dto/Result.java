package com.aiagent.common.dto;

import com.aiagent.common.exception.BaseException;
import com.aiagent.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * Unified API response wrapper.
 *
 * @param <T> the type of response data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Result<T>(
        boolean success,
        int code,
        String message,
        T data,
        Map<String, Object> metadata,
        OffsetDateTime timestamp
) {

    public Result {
        Objects.requireNonNull(message, "message must not be null");
        metadata = metadata != null ? Map.copyOf(metadata) : null;
        timestamp = timestamp != null ? timestamp : OffsetDateTime.now();
    }

    /**
     * Create a success response with data.
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(true, 0, "success", data, null, OffsetDateTime.now());
    }

    /**
     * Create a success response with data and message.
     */
    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(true, 0, message, data, null, OffsetDateTime.now());
    }

    /**
     * Create a success response without data.
     */
    public static <T> Result<T> ok() {
        return new Result<>(true, 0, "success", null, null, OffsetDateTime.now());
    }

    /**
     * Create an error response.
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(false, code, message, null, null, OffsetDateTime.now());
    }

    /**
     * Create an error response with metadata.
     */
    public static <T> Result<T> fail(int code, String message, Map<String, Object> metadata) {
        return new Result<>(false, code, message, null, metadata, OffsetDateTime.now());
    }

    /**
     * Create an error response from an ErrorCode.
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(false, errorCode.code(), errorCode.message(), null, null, OffsetDateTime.now());
    }

    /**
     * Create an error response from an ErrorCode with custom message.
     */
    public static <T> Result<T> fail(ErrorCode errorCode, String message) {
        return new Result<>(false, errorCode.code(), message, null, null, OffsetDateTime.now());
    }

    /**
     * Create an error response from a BaseException.
     */
    public static <T> Result<T> fail(BaseException exception) {
        return new Result<>(
                false,
                exception.getErrorCode().code(),
                exception.getMessage(),
                null,
                exception.getContext().isEmpty() ? null : exception.getContext(),
                OffsetDateTime.now()
        );
    }

    /**
     * Create a success response with data and metadata.
     */
    public static <T> Result<T> ok(T data, Map<String, Object> metadata) {
        return new Result<>(true, 0, "success", data, metadata, OffsetDateTime.now());
    }
}
