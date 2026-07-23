package com.aiagent.kernel.model;

import com.aiagent.kernel.context.AgentState;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record AgentResult(
        boolean success, String output, AgentState finalState,
        int iterations, Duration duration, List<AgentStep> steps,
        String errorMessage, Instant timestamp) {
    public AgentResult {
        Objects.requireNonNull(finalState, "finalState must not be null");
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        steps = (steps == null) ? List.of() : List.copyOf(steps);
    }
    public static AgentResult success(String output, int iterations, Duration duration, List<AgentStep> steps) {
        return new AgentResult(true, output, AgentState.COMPLETED, iterations, duration, steps, null, Instant.now());
    }
    public static AgentResult failure(String errorMessage, int iterations, Duration duration) {
        return new AgentResult(false, null, AgentState.FAILED, iterations, duration, List.of(), errorMessage, Instant.now());
    }
    public boolean isFailure() { return !success; }
}
