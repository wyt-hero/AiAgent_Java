package com.aiagent.kernel.lifecycle;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.context.AgentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DefaultAgentLifecycle implements AgentLifecycle {
    private static final Logger log = LoggerFactory.getLogger(DefaultAgentLifecycle.class);
    @Override public AgentContext onInit(AgentContext ctx) {
        log.info("Agent [{}] initializing", ctx.agentId());
        return ctx.withState(AgentState.RUNNING);
    }
    @Override public AgentContext beforeIteration(AgentContext ctx) { return ctx; }
    @Override public AgentContext afterIteration(AgentContext ctx) { return ctx; }
    @Override public void onComplete(AgentContext ctx) { log.info("Agent [{}] completed", ctx.agentId()); }
    @Override public void onFailure(AgentContext ctx, Throwable e) { log.error("Agent [{}] failed: {}", ctx.agentId(), e.getMessage()); }
    @Override public void onCancel(AgentContext ctx) { log.warn("Agent [{}] cancelled", ctx.agentId()); }
}
