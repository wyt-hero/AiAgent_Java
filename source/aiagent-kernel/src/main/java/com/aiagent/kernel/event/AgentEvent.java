package com.aiagent.kernel.event;
import com.aiagent.kernel.context.AgentContext;
import java.time.Instant;
public sealed interface AgentEvent permits
        AgentEvent.AgentStarted, AgentEvent.AgentCompleted, AgentEvent.AgentFailed,
        AgentEvent.AgentCancelled, AgentEvent.IterationStarted, AgentEvent.IterationCompleted, AgentEvent.StepExecuted {
    record AgentStarted(AgentContext context, Instant timestamp) implements AgentEvent {
        public AgentStarted(AgentContext context) { this(context, Instant.now()); }
    }
    record AgentCompleted(AgentContext context, String output, Instant timestamp) implements AgentEvent {
        public AgentCompleted(AgentContext context, String output) { this(context, output, Instant.now()); }
    }
    record AgentFailed(AgentContext context, Throwable cause, Instant timestamp) implements AgentEvent {
        public AgentFailed(AgentContext context, Throwable cause) { this(context, cause, Instant.now()); }
    }
    record AgentCancelled(AgentContext context, Instant timestamp) implements AgentEvent {
        public AgentCancelled(AgentContext context) { this(context, Instant.now()); }
    }
    record IterationStarted(AgentContext context, int iteration, Instant timestamp) implements AgentEvent {
        public IterationStarted(AgentContext context, int iteration) { this(context, iteration, Instant.now()); }
    }
    record IterationCompleted(AgentContext context, int iteration, Instant timestamp) implements AgentEvent {
        public IterationCompleted(AgentContext context, int iteration) { this(context, iteration, Instant.now()); }
    }
    record StepExecuted(AgentContext context, String phase, String content, Instant timestamp) implements AgentEvent {
        public StepExecuted(AgentContext context, String phase, String content) { this(context, phase, content, Instant.now()); }
    }
}
