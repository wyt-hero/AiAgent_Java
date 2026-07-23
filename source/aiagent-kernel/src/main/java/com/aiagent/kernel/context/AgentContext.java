package com.aiagent.kernel.context;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record AgentContext(
        String contextId,
        String agentId,
        String sessionId,
        AgentState state,
        String userMessage,
        List<String> conversationHistory,
        Map<String, String> metadata,
        int maxIterations,
        Instant createdAt,
        int currentIteration
) {
    public AgentContext {
        Objects.requireNonNull(contextId, "contextId must not be null");
        Objects.requireNonNull(agentId, "agentId must not be null");
        Objects.requireNonNull(sessionId, "sessionId must not be null");
        Objects.requireNonNull(state, "state must not be null");
        Objects.requireNonNull(userMessage, "userMessage must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        if (maxIterations < 1) throw new IllegalArgumentException("maxIterations must be >= 1");
        if (currentIteration < 0) throw new IllegalArgumentException("currentIteration must be >= 0");
        conversationHistory = (conversationHistory == null) ? List.of() : List.copyOf(conversationHistory);
        metadata = (metadata == null) ? Map.of() : Map.copyOf(metadata);
    }

    public AgentContext withState(AgentState newState) {
        return new AgentContext(contextId, agentId, sessionId, newState, userMessage,
                conversationHistory, metadata, maxIterations, createdAt, currentIteration);
    }

    public AgentContext withNextIteration() {
        return new AgentContext(contextId, agentId, sessionId, state, userMessage,
                conversationHistory, metadata, maxIterations, createdAt, currentIteration + 1);
    }

    public AgentContext withConversationHistory(List<String> history) {
        return new AgentContext(contextId, agentId, sessionId, state, userMessage,
                history, metadata, maxIterations, createdAt, currentIteration);
    }

    public AgentContext withMetadataEntry(String key, String value) {
        var newMeta = new java.util.HashMap<>(metadata);
        newMeta.put(key, value);
        return new AgentContext(contextId, agentId, sessionId, state, userMessage,
                conversationHistory, Map.copyOf(newMeta), maxIterations, createdAt, currentIteration);
    }

    public boolean isMaxIterationsReached() { return currentIteration >= maxIterations; }
    public boolean isRunning() { return state == AgentState.RUNNING; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String agentId;
        private String sessionId;
        private String userMessage;
        private List<String> conversationHistory;
        private Map<String, String> metadata;
        private int maxIterations = 10;

        private Builder() {}
        public Builder agentId(String agentId) { this.agentId = agentId; return this; }
        public Builder sessionId(String sessionId) { this.sessionId = sessionId; return this; }
        public Builder userMessage(String userMessage) { this.userMessage = userMessage; return this; }
        public Builder conversationHistory(List<String> h) { this.conversationHistory = h; return this; }
        public Builder metadata(Map<String, String> m) { this.metadata = m; return this; }
        public Builder maxIterations(int m) { this.maxIterations = m; return this; }

        public AgentContext build() {
            return new AgentContext(
                    UUID.randomUUID().toString(),
                    Objects.requireNonNull(agentId, "agentId is required"),
                    sessionId != null ? sessionId : UUID.randomUUID().toString(),
                    AgentState.IDLE,
                    userMessage != null ? userMessage : "",
                    conversationHistory, metadata, maxIterations,
                    Instant.now(), 0);
        }
    }
}
