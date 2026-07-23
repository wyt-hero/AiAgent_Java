package com.aiagent.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidationException Tests")
class ValidationExceptionTest {

    @Test
    @DisplayName("should_CreateValidationException_When_MultipleFieldErrors")
    void should_CreateValidationException_When_MultipleFieldErrors() {
        List<ValidationException.FieldError> errors = List.of(
                new ValidationException.FieldError("agentId", null, "must not be null"),
                new ValidationException.FieldError("model", "gpt-5", "unsupported model")
        );

        ValidationException ex = ValidationException.of(errors);

        assertEquals(CommonErrorCode.INVALID_ARGUMENT, ex.getErrorCode());
        assertEquals(400, ex.httpStatus());
        assertEquals(2, ex.getFieldErrors().size());
        assertTrue(ex.getMessage().contains("2 error(s)"));
    }

    @Test
    @DisplayName("should_CreateValidationException_When_SingleFieldError")
    void should_CreateValidationException_When_SingleFieldError() {
        ValidationException ex = ValidationException.of("name", null, "must not be blank");

        assertEquals(1, ex.getFieldErrors().size());
        assertEquals("name", ex.getFieldErrors().get(0).field());
        assertNull(ex.getFieldErrors().get(0).rejectedValue());
        assertEquals("must not be blank", ex.getFieldErrors().get(0).message());
    }

    @Test
    @DisplayName("should_PreserveRejectedValue_When_ValueProvided")
    void should_PreserveRejectedValue_When_ValueProvided() {
        ValidationException ex = ValidationException.of("age", -1, "must be positive");

        assertEquals(-1, ex.getFieldErrors().get(0).rejectedValue());
    }

    @Test
    @DisplayName("should_ReturnImmutableFieldErrors_When_Accessed")
    void should_ReturnImmutableFieldErrors_When_Accessed() {
        ValidationException ex = ValidationException.of("field", null, "error");

        assertThrows(UnsupportedOperationException.class, () ->
                ex.getFieldErrors().add(new ValidationException.FieldError("x", null, "y"))
        );
    }

    @Test
    @DisplayName("should_ThrowNPE_When_FieldErrorsIsNull")
    void should_ThrowNPE_When_FieldErrorsIsNull() {
        assertThrows(NullPointerException.class, () ->
                new ValidationException(CommonErrorCode.INVALID_ARGUMENT, (List<ValidationException.FieldError>) null)
        );
    }

    @Test
    @DisplayName("should_ThrowNPE_When_FieldNameIsNull")
    void should_ThrowNPE_When_FieldNameIsNull() {
        assertThrows(NullPointerException.class, () ->
                new ValidationException.FieldError(null, null, "error")
        );
    }

    @Test
    @DisplayName("should_ThrowNPE_When_FieldMessageIsNull")
    void should_ThrowNPE_When_FieldMessageIsNull() {
        assertThrows(NullPointerException.class, () ->
                new ValidationException.FieldError("field", null, null)
        );
    }

    @Test
    @DisplayName("should_CreateWithCustomMessage_When_Provided")
    void should_CreateWithCustomMessage_When_Provided() {
        List<ValidationException.FieldError> errors = List.of(
                new ValidationException.FieldError("x", null, "invalid")
        );
        ValidationException ex = new ValidationException(
                CommonErrorCode.INVALID_ARGUMENT, "Custom validation message", errors
        );

        assertEquals("Custom validation message", ex.getMessage());
        assertEquals(1, ex.getFieldErrors().size());
    }
}
