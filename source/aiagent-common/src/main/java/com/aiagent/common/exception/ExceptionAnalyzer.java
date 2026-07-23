package com.aiagent.common.exception;

import java.util.List;
import java.util.Objects;

/**
 * Analyzes exceptions and produces structured analysis results.
 * Used for consistent error handling, logging, and recovery suggestions.
 */
public interface ExceptionAnalyzer {

    /**
     * Analyze an exception and produce a structured result.
     *
     * @param throwable the exception to analyze
     * @return structured analysis result
     */
    AnalysisResult analyze(Throwable throwable);

    /**
     * Structured result of exception analysis.
     */
    record AnalysisResult(
            Severity severity,
            String category,
            String errorCode,
            String summary,
            boolean retryable,
            List<String> suggestions
    ) {
        public AnalysisResult {
            Objects.requireNonNull(severity, "severity must not be null");
            Objects.requireNonNull(category, "category must not be null");
            Objects.requireNonNull(errorCode, "errorCode must not be null");
            Objects.requireNonNull(summary, "summary must not be null");
            suggestions = suggestions != null ? List.copyOf(suggestions) : List.of();
        }
    }

    /**
     * Severity levels for exception analysis.
     */
    enum Severity {
        /** Recoverable, no action needed. */
        INFO,
        /** Requires investigation but not critical. */
        WARNING,
        /** Critical failure requiring immediate attention. */
        ERROR,
        /** System-level failure, potential data loss. */
        FATAL
    }
}
