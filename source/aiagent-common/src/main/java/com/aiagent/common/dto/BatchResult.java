package com.aiagent.common.dto;

import java.util.List;
import java.util.Objects;

/**
 * Result of a batch operation containing success/failure details.
 *
 * <p>Example:
 * <pre>{@code
 * BatchResult<AgentConfig> result = BatchResult.of(
 *     List.of(agent1, agent2),     // successes
 *     List.of(error1),              // errors
 *     3                             // total
 * );
 * }</pre>
 *
 * @param <T> the type of successfully processed items
 */
public record BatchResult<T>(
        List<T> successes,
        List<BatchError> errors,
        int total,
        int successCount,
        int errorCount
) {

    public BatchResult {
        Objects.requireNonNull(successes, "successes must not be null");
        Objects.requireNonNull(errors, "errors must not be null");
        if (total < 0) {
            throw new IllegalArgumentException("total must be >= 0, got: %d".formatted(total));
        }
        successes = List.copyOf(successes);
        errors = List.copyOf(errors);
    }

    /**
     * Create a batch result from successes and errors.
     */
    public static <T> BatchResult<T> of(List<T> successes, List<BatchError> errors, int total) {
        return new BatchResult<>(successes, errors, total, successes.size(), errors.size());
    }

    /**
     * Create a fully successful batch result.
     */
    public static <T> BatchResult<T> allSuccess(List<T> items) {
        return new BatchResult<>(items, List.of(), items.size(), items.size(), 0);
    }

    /**
     * Create an empty batch result.
     */
    public static <T> BatchResult<T> empty() {
        return new BatchResult<>(List.of(), List.of(), 0, 0, 0);
    }

    /**
     * Whether all items were processed successfully.
     */
    public boolean isAllSuccess() {
        return errorCount == 0 && successCount == total;
    }

    /**
     * Whether there were any failures.
     */
    public boolean hasErrors() {
        return errorCount > 0;
    }

    /**
     * Whether the batch result is empty.
     */
    public boolean isEmpty() {
        return total == 0;
    }

    /**
     * Represents a single item failure in a batch operation.
     *
     * @param index   the index of the failed item in the original batch
     * @param code    error code
     * @param message human-readable error description
     */
    public record BatchError(
            int index,
            String code,
            String message
    ) {
        public BatchError {
            if (index < 0) {
                throw new IllegalArgumentException("index must be >= 0, got: %d".formatted(index));
            }
            Objects.requireNonNull(code, "code must not be null");
            Objects.requireNonNull(message, "message must not be null");
        }

        /**
         * Create a batch error.
         */
        public static BatchError of(int index, String code, String message) {
            return new BatchError(index, code, message);
        }
    }
}
