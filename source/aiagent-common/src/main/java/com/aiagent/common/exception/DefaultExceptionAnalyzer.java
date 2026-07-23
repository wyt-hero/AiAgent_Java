package com.aiagent.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of {@link ExceptionAnalyzer}.
 * Categorizes framework exceptions and provides severity/retry analysis.
 */
public class DefaultExceptionAnalyzer implements ExceptionAnalyzer {

    @Override
    public AnalysisResult analyze(Throwable throwable) {
        if (throwable instanceof BaseException baseException) {
            return analyzeBaseException(baseException);
        }
        if (throwable instanceof IllegalArgumentException) {
            return analyzeValidationError(throwable);
        }
        return analyzeUnknown(throwable);
    }

    private AnalysisResult analyzeBaseException(BaseException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        int code = errorCode.code();

        Severity severity = determineSeverity(exception);
        String category = determineCategory(code);
        boolean retryable = determineRetryable(code, exception);
        List<String> suggestions = generateSuggestions(code, exception);

        return new AnalysisResult(
                severity,
                category,
                errorCode.name() + ":" + code,
                exception.getMessage(),
                retryable,
                suggestions
        );
    }

    private AnalysisResult analyzeValidationError(Throwable throwable) {
        return new AnalysisResult(
                Severity.INFO,
                "VALIDATION",
                "VALIDATION_ERROR",
                throwable.getMessage(),
                false,
                List.of("Check input parameters", "Review API contract")
        );
    }

    private AnalysisResult analyzeUnknown(Throwable throwable) {
        return new AnalysisResult(
                Severity.ERROR,
                "UNKNOWN",
                "UNKNOWN_ERROR",
                throwable.getMessage() != null ? throwable.getMessage() : throwable.getClass().getSimpleName(),
                false,
                List.of("Investigate root cause", "Check system logs")
        );
    }

    private Severity determineSeverity(BaseException exception) {
        int httpStatus = exception.httpStatus();
        return switch (httpStatus) {
            case 400, 404 -> Severity.INFO;
            case 401, 403, 409, 429 -> Severity.WARNING;
            default -> Severity.ERROR;
        };
    }

    private String determineCategory(int code) {
        int categoryCode = code / 10000;
        return switch (categoryCode) {
            case 1 -> "GENERAL";
            case 2 -> "AUTH";
            case 3 -> "AGENT";
            case 4 -> "TOOL";
            case 5 -> "PROMPT";
            case 6 -> "MEMORY";
            case 7 -> "WORKFLOW";
            case 8 -> "MCP";
            case 9 -> "KNOWLEDGE";
            default -> "UNKNOWN";
        };
    }

    private boolean determineRetryable(int code, BaseException exception) {
        return switch (code) {
            case 10006, // TIMEOUT
                    30003, // AGENT_TIMEOUT
                    429, // rate limit
                    80001 // MCP_CONNECTION_FAILED
                    -> true;
            default -> false;
        };
    }

    private List<String> generateSuggestions(int code, BaseException exception) {
        List<String> suggestions = new ArrayList<>();

        int categoryCode = code / 10000;
        switch (categoryCode) {
            case 2 -> {
                suggestions.add("Verify authentication credentials");
                suggestions.add("Check token expiration");
            }
            case 3 -> {
                suggestions.add("Verify agent configuration");
                suggestions.add("Check agent state machine");
            }
            case 4 -> {
                suggestions.add("Verify tool registration");
                suggestions.add("Check tool parameters");
            }
            case 6 -> {
                suggestions.add("Check memory storage connectivity");
                suggestions.add("Verify memory configuration");
            }
            case 7 -> {
                suggestions.add("Check workflow graph definition");
                suggestions.add("Verify node dependencies");
            }
            case 8 -> {
                suggestions.add("Check MCP server availability");
                suggestions.add("Verify MCP protocol version");
            }
            default -> {
                suggestions.add("Check system logs for details");
                suggestions.add("Review error context: " + exception.getContext());
            }
        }

        if (determineRetryable(code, exception)) {
            suggestions.add(0, "Retry the operation");
        }

        return List.copyOf(suggestions);
    }
}
