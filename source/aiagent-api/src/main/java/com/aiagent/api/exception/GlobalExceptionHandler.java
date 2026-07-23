package com.aiagent.api.exception;

import com.aiagent.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * Global exception handler for all REST controllers.
 *
 * <p>Maps framework exceptions to standardized {@link ErrorResponse} objects
 * with appropriate HTTP status codes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle ValidationException — 400 Bad Request.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(
                ex.getErrorCode().code(),
                ex.getErrorCode().name(),
                ex.getMessage(),
                request.getRequestURI(),
                null,
                ex.getContext().isEmpty() ? null : ex.getContext()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle AgentException — varies by subtype.
     */
    @ExceptionHandler(AgentException.class)
    public ResponseEntity<ErrorResponse> handleAgentException(
            AgentException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle ToolException — varies by subtype.
     */
    @ExceptionHandler(ToolException.class)
    public ResponseEntity<ErrorResponse> handleToolException(
            ToolException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle PromptException — 500 Internal Server Error.
     */
    @ExceptionHandler(PromptException.class)
    public ResponseEntity<ErrorResponse> handlePromptException(
            PromptException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle MemoryException — 500 Internal Server Error.
     */
    @ExceptionHandler(MemoryException.class)
    public ResponseEntity<ErrorResponse> handleMemoryException(
            MemoryException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle WorkflowException — 500 Internal Server Error.
     */
    @ExceptionHandler(WorkflowException.class)
    public ResponseEntity<ErrorResponse> handleWorkflowException(
            WorkflowException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle McpException — 500 Internal Server Error.
     */
    @ExceptionHandler(McpException.class)
    public ResponseEntity<ErrorResponse> handleMcpException(
            McpException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle KnowledgeException — 500 Internal Server Error.
     */
    @ExceptionHandler(KnowledgeException.class)
    public ResponseEntity<ErrorResponse> handleKnowledgeException(
            KnowledgeException ex, HttpServletRequest request) {
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle any BaseException not caught by specific handlers.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex, HttpServletRequest request) {
        log.error("Unhandled framework exception at [{}]", request.getRequestURI(), ex);
        HttpStatus status = mapHttpStatus(ex.getErrorCode().httpStatus());
        ErrorResponse response = buildErrorResponse(ex, request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle Spring validation errors (e.g., @Valid failures).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.INVALID_ARGUMENT.code(),
                CommonErrorCode.INVALID_ARGUMENT.name(),
                "Validation failed: " + ex.getBindingResult().getFieldErrors().size() + " error(s)",
                request.getRequestURI(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle missing request parameters.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.INVALID_ARGUMENT.code(),
                CommonErrorCode.INVALID_ARGUMENT.name(),
                "Missing required parameter: " + ex.getParameterName(),
                request.getRequestURI(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle type mismatch in request parameters.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.INVALID_ARGUMENT.code(),
                CommonErrorCode.INVALID_ARGUMENT.name(),
                "Invalid type for parameter '%s'".formatted(ex.getName()),
                request.getRequestURI(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle 404 resource not found.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.NOT_FOUND.code(),
                CommonErrorCode.NOT_FOUND.name(),
                "Resource not found",
                request.getRequestURI(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Catch-all handler for unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception at [{}]", request.getRequestURI(), ex);
        ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.INTERNAL_ERROR.code(),
                CommonErrorCode.INTERNAL_ERROR.name(),
                "An unexpected error occurred",
                request.getRequestURI(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // --- Helper methods ---

    private ErrorResponse buildErrorResponse(BaseException ex, String path) {
        return ErrorResponse.of(
                ex.getErrorCode().code(),
                ex.getErrorCode().name(),
                ex.getMessage(),
                path,
                null,
                ex.getContext().isEmpty() ? null : ex.getContext()
        );
    }

    private HttpStatus mapHttpStatus(int httpStatus) {
        try {
            return HttpStatus.valueOf(httpStatus);
        } catch (IllegalArgumentException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
