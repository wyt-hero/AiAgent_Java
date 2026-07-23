package com.aiagent.api.dto;

import com.aiagent.common.dto.Result;
import com.aiagent.common.exception.BaseException;
import com.aiagent.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Standard API response wrapper for all REST endpoints.
 *
 * <p>Provides a consistent response envelope across all APIs:
 * <pre>{@code
 * {
 *   "success": true,
 *   "code": 0,
 *   "message": "success",
 *   "data": { ... },
 *   "timestamp": "2026-07-23T10:00:00Z"
 * }
 * }</pre>
 *
 * @param <T> the type of response data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response envelope")
public record ApiResponse<T>(
        @Schema(description = "Whether the request was successful") boolean success,
        @Schema(description = "Response code (0 for success)") int code,
        @Schema(description = "Human-readable message") String message,
        @Schema(description = "Response data payload") T data,
        @Schema(description = "Response timestamp") OffsetDateTime timestamp
) {

    public ApiResponse {
        Objects.requireNonNull(message, "message must not be null");
        timestamp = timestamp != null ? timestamp : OffsetDateTime.now();
    }

    /**
     * Create a success response with data.
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, 0, "success", data, OffsetDateTime.now());
    }

    /**
     * Create a success response with data and custom message.
     */
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, 0, message, data, OffsetDateTime.now());
    }

    /**
     * Create a success response without data.
     */
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(true, 0, "success", null, OffsetDateTime.now());
    }

    /**
     * Create an error response from code and message.
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(false, code, message, null, OffsetDateTime.now());
    }

    /**
     * Create an error response from an ErrorCode.
     */
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.code(), errorCode.message(), null, OffsetDateTime.now());
    }

    /**
     * Create an error response from a BaseException.
     */
    public static <T> ApiResponse<T> error(BaseException exception) {
        return new ApiResponse<>(
                false,
                exception.getErrorCode().code(),
                exception.getMessage(),
                null,
                OffsetDateTime.now()
        );
    }

    /**
     * Convert from a common Result to ApiResponse.
     */
    public static <T> ApiResponse<T> from(Result<T> result) {
        return new ApiResponse<>(
                result.success(),
                result.code(),
                result.message(),
                result.data(),
                result.timestamp()
        );
    }
}
