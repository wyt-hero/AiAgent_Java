package com.aiagent.kernel.executor;
import com.aiagent.kernel.Agent;
import com.aiagent.kernel.AgentExecutor;
import com.aiagent.kernel.config.AgentConfig;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.context.AgentState;
import com.aiagent.kernel.event.AgentEvent;
import com.aiagent.kernel.event.EventBus;
import com.aiagent.kernel.lifecycle.AgentLifecycle;
import com.aiagent.kernel.lifecycle.DefaultAgentLifecycle;
import com.aiagent.kernel.model.AgentResult;
import com.aiagent.kernel.model.AgentStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class DefaultAgentExecutor implements AgentExecutor {
    private static final Logger log = LoggerFactory.getLogger(DefaultAgentExecutor.class);
    private final Agent agent;
    private final AgentConfig config;
    private final AgentLifecycle lifecycle;
    private final EventBus eventBus;
    public DefaultAgentExecutor(Agent agent) {
        this(agent, AgentConfig.builder().build(), new DefaultAgentLifecycle(), new EventBus());
    }
    public DefaultAgentExecutor(Agent agent, AgentConfig config, AgentLifecycle lifecycle, EventBus eventBus) {
        this.agent = Objects.requireNonNull(agent, "agent must not be null");
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.lifecycle = Objects.requireNonNull(lifecycle, "lifecycle must not be null");
        this.eventBus = Objects.requireNonNull(eventBus, "eventBus must not be null");
    }
    @Override
    public Object execute(AgentContext context) {
        Objects.requireNonNull(context, "context must not be null");
        Instant startTime = Instant.now();
        List<AgentStep> steps = new ArrayList<>();
        AgentContext ctx = lifecycle.onInit(context);
        eventBus.publish(new AgentEvent.AgentStarted(ctx));
        try {
            while (ctx.isRunning() && !ctx.isMaxIterationsReached()) {
                if (isTimedOut(startTime)) {
                    log.warn("Agent [{}] timed out", agent.id());
                    return buildTimeoutResult(startTime, steps);
                }
                int iteration = ctx.currentIteration();
                ctx = lifecycle.beforeIteration(ctx);
                eventBus.publish(new AgentEvent.IterationStarted(ctx, iteration));
                String thought = think(ctx, iteration);
                steps.add(AgentStep.of(iteration + 1, AgentStep.StepPhase.THINK, thought));
                eventBus.publish(new AgentEvent.StepExecuted(ctx, "THINK", thought));
                String action = act(ctx, iteration, thought);
                steps.add(AgentStep.of(iteration + 1, AgentStep.StepPhase.ACT, action));
                eventBus.publish(new AgentEvent.StepExecuted(ctx, "ACT", action));
                String observation = observe(ctx, iteration, action);
                steps.add(AgentStep.of(iteration + 1, AgentStep.StepPhase.OBSERVE, observation));
                eventBus.publish(new AgentEvent.StepExecuted(ctx, "OBSERVE", observation));
                ctx = ctx.withNextIteration();
                ctx = lifecycle.afterIteration(ctx);
                eventBus.publish(new AgentEvent.IterationCompleted(ctx, iteration));
                if (isComplete(observation)) {
                    ctx = ctx.withState(AgentState.COMPLETED);
                    lifecycle.onComplete(ctx);
                    eventBus.publish(new AgentEvent.AgentCompleted(ctx, observation));
                    return AgentResult.success(observation, ctx.currentIteration(), Duration.between(startTime, Instant.now()), steps);
                }
            }
            if (ctx.isMaxIterationsReached()) {
                ctx = ctx.withState(AgentState.COMPLETED);
                lifecycle.onComplete(ctx);
                String output = steps.isEmpty() ? "" : steps.getLast().content();
                return AgentResult.success(output, ctx.currentIteration(), Duration.between(startTime, Instant.now()), steps);
            }
            return AgentResult.failure("Execution ended without result", ctx.currentIteration(), Duration.between(startTime, Instant.now()));
        } catch (Exception e) {
            log.error("Agent [{}] failed: {}", agent.id(), e.getMessage(), e);
            ctx = ctx.withState(AgentState.FAILED);
            lifecycle.onFailure(ctx, e);
            eventBus.publish(new AgentEvent.AgentFailed(ctx, e));
            return AgentResult.failure(e.getMessage(), ctx.currentIteration(), Duration.between(startTime, Instant.now()));
        }
    }
    @Override public Agent agent() { return agent; }
    protected String think(AgentContext context, int iteration) { return "Thinking at iteration " + iteration; }
    protected String act(AgentContext context, int iteration, String thought) { return "Acting on: " + thought; }
    protected String observe(AgentContext context, int iteration, String action) { return "Observed: " + action; }
    protected boolean isComplete(String observation) { return false; }
    private boolean isTimedOut(Instant startTime) { return Duration.between(startTime, Instant.now()).compareTo(config.timeout()) > 0; }
    private AgentResult buildTimeoutResult(Instant startTime, List<AgentStep> steps) {
        return AgentResult.failure("Execution timed out after " + config.timeout(), steps.size() / 3, Duration.between(startTime, Instant.now()));
    }
}
