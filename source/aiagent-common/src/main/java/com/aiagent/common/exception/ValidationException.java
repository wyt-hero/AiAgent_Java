package com.aiagent.common.exception;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Exception thrown when validation fails.
 * Supports field-level error details for structured validation reporting.
 *
 * <p>Example usage:
 * <pre>{@code
 * throw ValidationException.of(List.of(
 *     new ValidationException.FieldError("agentId", null, "must not be null"),
 *     new ValidationException.FieldError("model", "gpt-5", "unsupported model")
 * ));
 * }</pre>
 */
@Getter
public class ValidationException extends BaseException {

    private final List<FieldError> fieldErrors;

    public ValidationException(ErrorCode errorCode, List<FieldError> fieldErrors) {
        super(errorCode, buildMessage(fieldErrors), null, buildContext(fieldErrors));
        this.fieldErrors = List.copyOf(Objects.requireNonNull(fieldErrors, "fieldErrors must not be null"));
    }

    public ValidationException(ErrorCode errorCode, String message, List<FieldError> fieldErrors) {
        super(errorCode, message, null, buildContext(fieldErrors));
        this.fieldErrors = List.copyOf(Objects.requireNonNull(fieldErrors, "fieldErrors must not be null"));
    }

    /**
     * Create a ValidationException from field errors.
     */
    public static ValidationException of(List<FieldError> fieldErrors) {
        return new ValidationException(CommonErrorCode.INVALID_ARGUMENT, fieldErrors);
    }

    /**
     * Create a ValidationException for a single field error.
     */
    public static ValidationException of(String field, Object rejectedValue, String reason) {
        return new ValidationException(
                CommonErrorCode.INVALID_ARGUMENT,
                List.of(new FieldError(field, rejectedValue, reason))
        );
    }

    private static String buildMessage(List<FieldError> fieldErrors) {
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return "Validation failed";
        }
        return "Validation failed: %d error(s)".formatted(fieldErrors.size());
    }

    private static Map<String, Object> buildContext(List<FieldError> fieldErrors) {
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return Map.of();
        }
        return Map.of("fieldCount", fieldErrors.size());
    }

    /**
     * Represents a single field-level validation error.
     *
     * @param field         the field name that failed validation
     * @param rejectedValue the value that was rejected (may be null)
     * @param message       human-readable error description
     */
    public record FieldError(
            String field,
            Object rejectedValue,
            String message
    ) {
        public FieldError {
            Objects.requireNonNull(field, "field must not be null");
            Objects.requireNonNull(message, "message must not be null");
        }
    }
}
