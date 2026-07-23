package com.aiagent.kernel;

import com.aiagent.kernel.context.AgentContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Agent & AgentExecutor Interface Tests")
class AgentInterfaceTest {

    @Nested
    @DisplayName("Agent interface")
    class AgentTests {
        @Test
        @DisplayName("should_ReturnIdAndName")
        void should_ReturnIdAndName() {
            Agent agent = new Agent() {
                @Override public String id() { return "test-agent"; }
                @Override public String name() { return "Test Agent"; }
            };
            assertEquals("test-agent", agent.id());
            assertEquals("Test Agent", agent.name());
        }

        @Test
        @DisplayName("should_ReturnDefaults_When NotOverridden")
        void should_ReturnDefaults_When_NotOverridden() {
            Agent agent = new Agent() {
                @Override public String id() { return "t"; }
                @Override public String name() { return "T"; }
            };
            assertEquals("", agent.description());
            assertTrue(agent.isAvailable());
        }

        @Test
        @DisplayName("should_ReturnCustomValues_When Overridden")
        void should_ReturnCustomValues_When_Overridden() {
            Agent agent = new Agent() {
                @Override public String id() { return "c"; }
                @Override public String name() { return "C"; }
                @Override public String description() { return "desc"; }
                @Override public boolean isAvailable() { return false; }
            };
            assertEquals("desc", agent.description());
            assertFalse(agent.isAvailable());
        }
    }

    @Nested
    @DisplayName("AgentExecutor interface")
    class ExecutorTests {
        @Test
        @DisplayName("should_ExecuteSynchronously")
        void should_ExecuteSynchronously() {
            Agent agent = new Agent() {
                @Override public String id() { return "t"; }
                @Override public String name() { return "T"; }
            };
            AgentExecutor executor = new AgentExecutor() {
                @Override public Object execute(AgentContext ctx) { return "result"; }
                @Override public Agent agent() { return agent; }
            };
            AgentContext ctx = AgentContext.builder().agentId("t").userMessage("hi").build();
            assertEquals("result", executor.execute(ctx));
            assertSame(agent, executor.agent());
        }

        @Test
        @DisplayName("should_ExecuteAsynchronously_When UsingDefault")
        void should_ExecuteAsynchronously_When_UsingDefault() throws Exception {
            Agent agent = new Agent() {
                @Override public String id() { return "t"; }
                @Override public String name() { return "T"; }
            };
            AgentExecutor executor = new AgentExecutor() {
                @Override public Object execute(AgentContext ctx) { return "async"; }
                @Override public Agent agent() { return agent; }
            };
            AgentContext ctx = AgentContext.builder().agentId("t").userMessage("hi").build();
            assertEquals("async", executor.executeAsync(ctx).get());
        }
    }
}
