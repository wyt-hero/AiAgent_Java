package com.aiagent.kernel.config;

import com.aiagent.kernel.context.AgentState;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public record AgentConfig(
        int maxIterations, Duration timeout, int maxRetries, Duration retryDelay,
        AgentState initialState, boolean recordSteps, boolean emitEvents,
        Map<String, String> properties) {
    public AgentConfig {
        if (maxIterations < 1) throw new IllegalArgumentException("maxIterations must be >= 1");
        Objects.requireNonNull(timeout, "timeout must not be null");
        if (maxRetries < 0) throw new IllegalArgumentException("maxRetries must be >= 0");
        Objects.requireNonNull(retryDelay, "retryDelay must not be null");
        Objects.requireNonNull(initialState, "initialState must not be null");
        properties = (properties == null) ? Map.of() : Map.copyOf(properties);
    }
    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private int maxIterations = 10;
        private Duration timeout = Duration.ofMinutes(5);
        private int maxRetries = 0;
        private Duration retryDelay = Duration.ofSeconds(1);
        private AgentState initialState = AgentState.IDLE;
        private boolean recordSteps = true;
        private boolean emitEvents = true;
        private Map<String, String> properties;
        private Builder() {}
        public Builder maxIterations(int v) { this.maxIterations = v; return this; }
        public Builder timeout(Duration v) { this.timeout = v; return this; }
        public Builder maxRetries(int v) { this.maxRetries = v; return this; }
        public Builder retryDelay(Duration v) { this.retryDelay = v; return this; }
        public Builder initialState(AgentState v) { this.initialState = v; return this; }
        public Builder recordSteps(boolean v) { this.recordSteps = v; return this; }
        public Builder emitEvents(boolean v) { this.emitEvents = v; return this; }
        public Builder properties(Map<String, String> v) { this.properties = v; return this; }
        public Builder property(String key, String value) {
            var map = new java.util.HashMap<>(this.properties != null ? this.properties : Map.of());
            map.put(key, value);
            this.properties = map;
            return this;
        }
        public AgentConfig build() {
            return new AgentConfig(maxIterations, timeout, maxRetries, retryDelay,
                    initialState, recordSteps, emitEvents, properties);
        }
    }
}
