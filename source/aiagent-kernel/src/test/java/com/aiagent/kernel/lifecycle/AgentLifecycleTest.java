package com.aiagent.kernel.lifecycle;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.context.AgentState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class AgentLifecycleTest {
    private final AgentContext ctx = AgentContext.builder().agentId("test-agent").userMessage("hello").build();
    @Test
    void defaultLifecycle_onInit_shouldSetRunningState() {
        var lifecycle = new DefaultAgentLifecycle();
        var result = lifecycle.onInit(ctx);
        assertEquals(AgentState.RUNNING, result.state());
    }
    @Test
    void defaultLifecycle_beforeIteration_shouldReturnSameContext() {
        var lifecycle = new DefaultAgentLifecycle();
        assertSame(ctx, lifecycle.beforeIteration(ctx));
    }
    @Test
    void defaultLifecycle_afterIteration_shouldReturnSameContext() {
        var lifecycle = new DefaultAgentLifecycle();
        assertSame(ctx, lifecycle.afterIteration(ctx));
    }
    @Test
    void defaultLifecycle_onComplete_shouldNotThrow() {
        assertDoesNotThrow(() -> new DefaultAgentLifecycle().onComplete(ctx));
    }
    @Test
    void defaultLifecycle_onFailure_shouldNotThrow() {
        assertDoesNotThrow(() -> new DefaultAgentLifecycle().onFailure(ctx, new RuntimeException("err")));
    }
    @Test
    void defaultLifecycle_onCancel_shouldNotThrow() {
        assertDoesNotThrow(() -> new DefaultAgentLifecycle().onCancel(ctx));
    }
    @Test
    void isValidTransition_idleToRunning_shouldBeValid() {
        var lifecycle = new AgentLifecycle() {};
        assertTrue(lifecycle.isValidTransition(AgentState.IDLE, AgentState.RUNNING));
    }
    @Test
    void isValidTransition_idleToCancelled_shouldBeValid() {
        assertTrue(new AgentLifecycle() {}.isValidTransition(AgentState.IDLE, AgentState.CANCELLED));
    }
    @Test
    void isValidTransition_idleToCompleted_shouldBeInvalid() {
        assertFalse(new AgentLifecycle() {}.isValidTransition(AgentState.IDLE, AgentState.COMPLETED));
    }
    @Test
    void isValidTransition_runningToCompleted_shouldBeValid() {
        assertTrue(new AgentLifecycle() {}.isValidTransition(AgentState.RUNNING, AgentState.COMPLETED));
    }
    @Test
    void isValidTransition_runningToFailed_shouldBeValid() {
        assertTrue(new AgentLifecycle() {}.isValidTransition(AgentState.RUNNING, AgentState.FAILED));
    }
    @Test
    void isValidTransition_completedToAny_shouldBeInvalid() {
        var l = new AgentLifecycle() {};
        assertFalse(l.isValidTransition(AgentState.COMPLETED, AgentState.IDLE));
        assertFalse(l.isValidTransition(AgentState.COMPLETED, AgentState.RUNNING));
    }
    @Test
    void isValidTransition_failedToAny_shouldBeInvalid() {
        var l = new AgentLifecycle() {};
        assertFalse(l.isValidTransition(AgentState.FAILED, AgentState.IDLE));
        assertFalse(l.isValidTransition(AgentState.FAILED, AgentState.RUNNING));
    }
}
