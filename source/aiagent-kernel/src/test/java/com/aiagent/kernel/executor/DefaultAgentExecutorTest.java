package com.aiagent.kernel.executor;
import com.aiagent.kernel.Agent;
import com.aiagent.kernel.config.AgentConfig;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.event.AgentEvent;
import com.aiagent.kernel.event.EventBus;
import com.aiagent.kernel.lifecycle.DefaultAgentLifecycle;
import com.aiagent.kernel.model.AgentResult;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class DefaultAgentExecutorTest {
    private final Agent testAgent = new Agent() {
        @Override public String id() { return "test-agent"; }
        @Override public String name() { return "Test Agent"; }
    };
    private final AgentContext ctx = AgentContext.builder().agentId("test-agent").userMessage("hello").maxIterations(5).build();
    @Test
    void execute_shouldRunAndReturnResult() {
        var executor = new DefaultAgentExecutor(testAgent);
        var result = (AgentResult) executor.execute(ctx);
        assertNotNull(result);
        assertTrue(result.success());
        assertTrue(result.iterations() > 0);
        assertFalse(result.steps().isEmpty());
    }
    @Test
    void execute_shouldRespectMaxIterations() {
        var result = (AgentResult) new DefaultAgentExecutor(testAgent).execute(ctx);
        assertTrue(result.iterations() <= 5);
    }
    @Test
    void execute_shouldEmitEvents() {
        var eventBus = new EventBus();
        List<AgentEvent> events = new ArrayList<>();
        eventBus.register(events::add);
        var executor = new DefaultAgentExecutor(testAgent, AgentConfig.builder().build(), new DefaultAgentLifecycle(), eventBus);
        executor.execute(ctx);
        assertFalse(events.isEmpty());
        assertTrue(events.stream().anyMatch(e -> e instanceof AgentEvent.AgentStarted));
    }
    @Test
    void execute_shouldHandleException() {
        var failing = new DefaultAgentExecutor(testAgent) {
            @Override protected String think(AgentContext context, int iteration) { throw new RuntimeException("think failed"); }
        };
        var result = (AgentResult) failing.execute(ctx);
        assertFalse(result.success());
        assertTrue(result.isFailure());
        assertNotNull(result.errorMessage());
    }
    @Test
    void execute_shouldRejectNullContext() {
        assertThrows(NullPointerException.class, () -> new DefaultAgentExecutor(testAgent).execute(null));
    }
    @Test
    void constructor_shouldRejectNullAgent() {
        assertThrows(NullPointerException.class, () -> new DefaultAgentExecutor(null));
    }
    @Test
    void agent_shouldReturnConfiguredAgent() {
        assertEquals("test-agent", new DefaultAgentExecutor(testAgent).agent().id());
    }
    @Test
    void execute_withCompletion_shouldStopEarly() {
        var executor = new DefaultAgentExecutor(testAgent) {
            @Override protected boolean isComplete(String observation) { return true; }
        };
        var result = (AgentResult) executor.execute(ctx);
        assertTrue(result.success());
        assertEquals(1, result.iterations());
    }
    @Test
    void execute_shouldCollectSteps() {
        var executor = new DefaultAgentExecutor(testAgent) {
            @Override protected boolean isComplete(String observation) { return observation.contains("Acting on"); }
        };
        var result = (AgentResult) executor.execute(ctx);
        assertTrue(result.success());
        assertEquals(3, result.steps().size());
    }
}
