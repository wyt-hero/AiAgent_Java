package com.aiagent.kernel.lifecycle;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.context.AgentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DefaultAgentLifecycle implements AgentLifecycle {
    private static final Logger log = LoggerFactory.getLogger(DefaultAgentLifecycle.class);
    @Override
    public AgentContext onInit(AgentContext context) {
        log.info("Agent [{}] initializing, session [{}]", context.agentId(), context.sessionId());
        return context.withState(AgentState.RUNNING);
    }
    @Override
    public AgentContext beforeIteration(AgentContext context) {
        log.debug("Agent [{}] starting iteration [{}]", context.agentId(), context.currentIteration());
        return context;
    }
    @Override
    public AgentContext afterIteration(AgentContext context) {
        log.debug("Agent [{}] completed iteration [{}]", context.agentId(), context.currentIteration());
        return context;
    }
    @Override
    public void onComplete(AgentContext context) {
        log.info("Agent [{}] completed after [{}] iterations", context.agentId(), context.currentIteration());
    }
    @Override
    public void onFailure(AgentContext context, Throwable cause) {
        log.error("Agent [{}] failed after [{}] iterations: {}", context.agentId(), context.currentIteration(), cause.getMessage());
    }
    @Override
    public void onCancel(AgentContext context) {
        log.warn("Agent [{}] cancelled at iteration [{}]", context.agentId(), context.currentIteration());
    }
}
