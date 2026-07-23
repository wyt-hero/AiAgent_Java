package com.aiagent.kernel.model;

import java.time.Instant;
import java.util.Objects;

public record AgentStep(
        int stepNumber, StepPhase phase, String content, Instant timestamp) {
    public AgentStep {
        Objects.requireNonNull(phase, "phase must not be null");
        Objects.requireNonNull(content, "content must not be null");
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        if (stepNumber < 1) throw new IllegalArgumentException("stepNumber must be >= 1");
    }
    public static AgentStep of(int number, StepPhase phase, String content) {
        return new AgentStep(number, phase, content, Instant.now());
    }
    public enum StepPhase { THINK, ACT, OBSERVE }
}
