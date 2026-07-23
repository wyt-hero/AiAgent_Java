package com.aiagent.kernel.context;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AgentContext Tests")
class AgentContextTest {

    @Nested
    @DisplayName("Builder")
    class BuilderTests {
        @Test
        @DisplayName("should_CreateContext_When RequiredFieldsProvided")
        void should_CreateContext_When_RequiredFieldsProvided() {
            AgentContext ctx = AgentContext.builder().agentId("agent-001").userMessage("Hello").build();
            assertNotNull(ctx.contextId());
            assertEquals("agent-001", ctx.agentId());
            assertEquals("Hello", ctx.userMessage());
            assertEquals(AgentState.IDLE, ctx.state());
            assertEquals(0, ctx.currentIteration());
            assertEquals(10, ctx.maxIterations());
        }

        @Test
        @DisplayName("should_Throw_When AgentIdMissing")
        void should_Throw_When_AgentIdMissing() {
            assertThrows(NullPointerException.class, () -> AgentContext.builder().build());
        }

        @Test
        @DisplayName("should_SetAllFields_When FullBuilder")
        void should_SetAllFields_When_FullBuilder() {
            AgentContext ctx = AgentContext.builder()
                    .agentId("a1").sessionId("s1").userMessage("hi")
                    .conversationHistory(List.of("hi", "hello"))
                    .metadata(Map.of("k", "v")).maxIterations(5).build();
            assertEquals("s1", ctx.sessionId());
            assertEquals(List.of("hi", "hello"), ctx.conversationHistory());
            assertEquals(Map.of("k", "v"), ctx.metadata());
            assertEquals(5, ctx.maxIterations());
        }
    }

    @Nested
    @DisplayName("Validation")
    class ValidationTests {
        @Test
        @DisplayName("should_Throw_When MaxIterationsLessThanOne")
        void should_Throw_When_MaxIterationsLessThanOne() {
            assertThrows(IllegalArgumentException.class, () ->
                    AgentContext.builder().agentId("a").maxIterations(0).build());
        }

        @Test
        @DisplayName("should_HandleNullCollections_When NullLists")
        void should_HandleNullCollections_When_NullLists() {
            AgentContext ctx = AgentContext.builder().agentId("a")
                    .conversationHistory(null).metadata(null).build();
            assertEquals(List.of(), ctx.conversationHistory());
            assertEquals(Map.of(), ctx.metadata());
        }
    }

    @Nested
    @DisplayName("State transitions")
    class StateTests {
        @Test
        @DisplayName("should_ReturnNewContext_When StateChanged")
        void should_ReturnNewContext_When_StateChanged() {
            AgentContext ctx = AgentContext.builder().agentId("a").build();
            AgentContext running = ctx.withState(AgentState.RUNNING);
            assertEquals(AgentState.IDLE, ctx.state());
            assertEquals(AgentState.RUNNING, running.state());
            assertEquals(ctx.contextId(), running.contextId());
        }
    }

    @Nested
    @DisplayName("Iterations")
    class IterationTests {
        @Test
        @DisplayName("should_IncrementIteration")
        void should_IncrementIteration() {
            AgentContext ctx = AgentContext.builder().agentId("a").maxIterations(3).build();
            AgentContext next = ctx.withNextIteration();
            assertEquals(0, ctx.currentIteration());
            assertEquals(1, next.currentIteration());
            assertFalse(ctx.isMaxIterationsReached());
        }

        @Test
        @DisplayName("should_ReturnTrue_When MaxIterationsReached")
        void should_ReturnTrue_When_MaxIterationsReached() {
            AgentContext ctx = AgentContext.builder().agentId("a").maxIterations(2).build();
            AgentContext iter2 = ctx.withNextIteration().withNextIteration();
            assertTrue(iter2.isMaxIterationsReached());
        }
    }

    @Nested
    @DisplayName("History and Metadata")
    class HistoryMetadataTests {
        @Test
        @DisplayName("should_UpdateHistory")
        void should_UpdateHistory() {
            AgentContext ctx = AgentContext.builder().agentId("a").build();
            AgentContext updated = ctx.withConversationHistory(List.of("m1", "m2"));
            assertEquals(List.of(), ctx.conversationHistory());
            assertEquals(List.of("m1", "m2"), updated.conversationHistory());
        }

        @Test
        @DisplayName("should_AddMetadataEntry")
        void should_AddMetadataEntry() {
            AgentContext ctx = AgentContext.builder().agentId("a").build();
            AgentContext updated = ctx.withMetadataEntry("key", "value");
            assertEquals(Map.of(), ctx.metadata());
            assertEquals("value", updated.metadata().get("key"));
        }
    }

    @Nested
    @DisplayName("isRunning()")
    class RunningTests {
        @Test
        @DisplayName("should_ReturnTrue_When Running")
        void should_ReturnTrue_When_Running() {
            AgentContext ctx = AgentContext.builder().agentId("a").build().withState(AgentState.RUNNING);
            assertTrue(ctx.isRunning());
        }

        @Test
        @DisplayName("should_ReturnFalse_When NotRunning")
        void should_ReturnFalse_When_NotRunning() {
            AgentContext ctx = AgentContext.builder().agentId("a").build();
            assertFalse(ctx.isRunning());
        }
    }
}
