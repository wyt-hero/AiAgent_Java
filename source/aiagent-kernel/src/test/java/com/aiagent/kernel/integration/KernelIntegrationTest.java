package com.aiagent.kernel.integration;

import com.aiagent.kernel.Agent;
import com.aiagent.kernel.config.AgentConfig;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.context.AgentState;
import com.aiagent.kernel.event.AgentEvent;
import com.aiagent.kernel.event.EventBus;
import com.aiagent.kernel.executor.DefaultAgentExecutor;
import com.aiagent.kernel.hook.AgentHook;
import com.aiagent.kernel.hook.HookRegistry;
import com.aiagent.kernel.lifecycle.AgentLifecycle;
import com.aiagent.kernel.lifecycle.DefaultAgentLifecycle;
import com.aiagent.kernel.model.AgentResult;
import com.aiagent.kernel.registry.AgentDescriptor;
import com.aiagent.kernel.registry.AgentRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the complete Agent Kernel (M2).
 *
 * <p>Verifies that all kernel components work together:
 * Agent, Executor, Config, Lifecycle, EventBus, Hooks, Registry.
 */
class KernelIntegrationTest {

    private AgentRegistry registry;
    private EventBus eventBus;
    private HookRegistry hookRegistry;
    private AgentLifecycle lifecycle;
    private List<AgentEvent> capturedEvents;

    private final Agent testAgent = new Agent() {
        @Override public String id() { return "integration-agent"; }
        @Override public String name() { return "Integration Agent"; }
    };

    @BeforeEach
    void setUp() {
        registry = new AgentRegistry();
        eventBus = new EventBus();
        hookRegistry = new HookRegistry();
        lifecycle = new DefaultAgentLifecycle();
        capturedEvents = new ArrayList<>();
        eventBus.register(capturedEvents::add);
    }

    @Test
    void fullExecutionPipeline_shouldProduceResult() {
        // Register agent
        registry.register(testAgent);

        // Create executor with all components
        var config = AgentConfig.builder()
                .maxIterations(3)
                .build();

        var executor = new DefaultAgentExecutor(testAgent, config, lifecycle, eventBus);

        // Build context
        var ctx = AgentContext.builder()
                .agentId("integration-agent")
                .userMessage("Test integration")
                .maxIterations(3)
                .build();

        // Execute
        var result = (AgentResult) executor.execute(ctx);

        // Verify result
        assertNotNull(result);
        assertTrue(result.success());
        assertTrue(result.iterations() > 0);
        assertFalse(result.steps().isEmpty());

        // Verify events were emitted
        assertFalse(capturedEvents.isEmpty());
        assertTrue(capturedEvents.stream().anyMatch(e -> e instanceof AgentEvent.AgentStarted));
        assertTrue(capturedEvents.stream().anyMatch(e -> e instanceof AgentEvent.AgentCompleted));

        // Verify agent is registered
        assertTrue(registry.contains("integration-agent"));
        assertEquals("Integration Agent", registry.get("integration-agent").name());
    }

    @Test
    void hooksShouldBeInvokedDuringExecution() {
        List<String> hookCalls = new ArrayList<>();

        hookRegistry.register(new AgentHook() {
            @Override public String name() { return "tracker"; }
            @Override public int order() { return 1; }
            @Override public void beforeExecute(com.aiagent.kernel.context.AgentContext context) {
                hookCalls.add("beforeExecute");
            }
            @Override public void afterExecute(com.aiagent.kernel.context.AgentContext context) {
                hookCalls.add("afterExecute");
            }
        });

        // Verify hooks fire correctly
        var ctx = AgentContext.builder()
                .agentId("test")
                .userMessage("hello")
                .build();

        hookRegistry.fireBeforeExecute(ctx);
        hookRegistry.fireAfterExecute(ctx);

        assertEquals(List.of("beforeExecute", "afterExecute"), hookCalls);
    }

    @Test
    void registryShouldManageMultipleAgents() {
        Agent agent1 = new Agent() {
            @Override public String id() { return "a1"; }
            @Override public String name() { return "Agent 1"; }
        };
        Agent agent2 = new Agent() {
            @Override public String id() { return "a2"; }
            @Override public String name() { return "Agent 2"; }
        };

        registry.register(agent1);
        registry.register(agent2);

        assertEquals(2, registry.size());
        assertEquals(2, registry.activeAgents().size());

        registry.setActive("a1", false);
        assertEquals(1, registry.activeAgents().size());
        assertEquals("a2", registry.activeAgents().get(0).agentId());
    }

    @Test
    void lifecycleShouldManageStateTransitions() {
        var ctx = AgentContext.builder()
                .agentId("lifecycle-test")
                .userMessage("test")
                .build();

        assertEquals(AgentState.IDLE, ctx.state());

        lifecycle.onInit(ctx);

        var runningCtx = ctx.withState(AgentState.RUNNING);
        assertTrue(lifecycle.isValidTransition(AgentState.IDLE, AgentState.RUNNING));
        assertTrue(lifecycle.isValidTransition(AgentState.RUNNING, AgentState.COMPLETED));
        assertFalse(lifecycle.isValidTransition(AgentState.COMPLETED, AgentState.RUNNING));
    }

    @Test
    void eventBusShouldDistributeToMultipleListeners() {
        List<AgentEvent> listener1 = new ArrayList<>();
        List<AgentEvent> listener2 = new ArrayList<>();

        eventBus.register(listener1::add);
        eventBus.register(listener2::add);

        var ctx = AgentContext.builder()
                .agentId("event-test")
                .userMessage("test")
                .build();
        var event = new AgentEvent.AgentStarted(ctx);
        eventBus.publish(event);

        assertEquals(1, listener1.size());
        assertEquals(1, listener2.size());
        assertInstanceOf(AgentEvent.AgentStarted.class, listener1.get(0));
    }

    @Test
    void executorWithEarlyCompletion_shouldStopAfterOneIteration() {
        var executor = new DefaultAgentExecutor(testAgent) {
            @Override
            protected boolean isComplete(String observation) {
                return true; // Complete after first observation
            }
        };

        var ctx = AgentContext.builder()
                .agentId("integration-agent")
                .userMessage("quick task")
                .maxIterations(10)
                .build();

        var result = (AgentResult) executor.execute(ctx);

        assertTrue(result.success());
        assertEquals(1, result.iterations());
    }

    @Test
    void agentDescriptorShouldCarryMetadata() {
        var desc = new AgentDescriptor("meta-agent", "Meta Agent", "Test desc",
                testAgent, true, Map.of("version", "2.0", "env", "test"));

        registry.register(desc);

        var found = registry.get("meta-agent");
        assertEquals("2.0", found.metadata().get("version"));
        assertEquals("test", found.metadata().get("env"));
        assertTrue(found.active());

        var updated = found.withActive(false).withMetadata("updated", "true");
        assertFalse(updated.active());
        assertEquals("true", updated.metadata().get("updated"));
    }

    @Test
    void configBuilder_shouldProvideDefaults() {
        var config = AgentConfig.builder().build();

        assertNotNull(config);
        assertTrue(config.maxIterations() > 0);
    }

    @Test
    void hookRegistryOrdering_shouldInvokeInPriorityOrder() {
        List<String> order = new ArrayList<>();

        hookRegistry.register(new AgentHook() {
            @Override public String name() { return "low"; }
            @Override public int order() { return 200; }
            @Override public void beforeExecute(AgentContext context) { order.add("low"); }
        });
        hookRegistry.register(new AgentHook() {
            @Override public String name() { return "high"; }
            @Override public int order() { return 1; }
            @Override public void beforeExecute(AgentContext context) { order.add("high"); }
        });
        hookRegistry.register(new AgentHook() {
            @Override public String name() { return "mid"; }
            @Override public int order() { return 100; }
            @Override public void beforeExecute(AgentContext context) { order.add("mid"); }
        });

        var ctx = AgentContext.builder()
                .agentId("test")
                .userMessage("order test")
                .build();

        hookRegistry.fireBeforeExecute(ctx);

        assertEquals(List.of("high", "mid", "low"), order);
    }
}
