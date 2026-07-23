package com.aiagent.kernel.model;
import com.aiagent.kernel.context.AgentState;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class AgentResultTest {
    @Test
    void successFactory_shouldCreateSuccessfulResult() {
        var steps = List.of(AgentStep.of(1, AgentStep.StepPhase.THINK, "thinking"));
        var result = AgentResult.success("done", 3, Duration.ofSeconds(5), steps);
        assertTrue(result.success());
        assertFalse(result.isFailure());
        assertEquals("done", result.output());
        assertEquals(AgentState.COMPLETED, result.finalState());
        assertEquals(3, result.iterations());
        assertEquals(Duration.ofSeconds(5), result.duration());
        assertEquals(1, result.steps().size());
        assertNull(result.errorMessage());
        assertNotNull(result.timestamp());
    }
    @Test
    void failureFactory_shouldCreateFailedResult() {
        var result = AgentResult.failure("something broke", 2, Duration.ofSeconds(1));
        assertFalse(result.success());
        assertTrue(result.isFailure());
        assertNull(result.output());
        assertEquals(AgentState.FAILED, result.finalState());
        assertEquals(2, result.iterations());
        assertEquals("something broke", result.errorMessage());
        assertTrue(result.steps().isEmpty());
    }
    @Test
    void constructor_shouldDefensiveCopySteps() {
        var steps = List.of(AgentStep.of(1, AgentStep.StepPhase.ACT, "acting"));
        var result = new AgentResult(true, "out", AgentState.COMPLETED, 1, Duration.ZERO, steps, null, Instant.now());
        assertNotSame(steps, result.steps());
        assertEquals(steps, result.steps());
    }
    @Test
    void constructor_shouldHandleNullStepsAsEmptyList() {
        var result = new AgentResult(true, "out", AgentState.COMPLETED, 1, Duration.ZERO, null, null, Instant.now());
        assertNotNull(result.steps());
        assertTrue(result.steps().isEmpty());
    }
    @Test
    void constructor_shouldRejectNullFinalState() {
        assertThrows(NullPointerException.class, () -> new AgentResult(true, "out", null, 1, Duration.ZERO, List.of(), null, Instant.now()));
    }
    @Test
    void constructor_shouldRejectNullTimestamp() {
        assertThrows(NullPointerException.class, () -> new AgentResult(true, "out", AgentState.COMPLETED, 1, Duration.ZERO, List.of(), null, null));
    }
    @Test
    void steps_shouldBeImmutable() {
        var result = AgentResult.success("out", 1, Duration.ZERO, List.of(AgentStep.of(1, AgentStep.StepPhase.OBSERVE, "observing")));
        assertThrows(UnsupportedOperationException.class, () -> result.steps().add(AgentStep.of(2, AgentStep.StepPhase.THINK, "x")));
    }
    @Test
    void isFailure_shouldBeInverseOfSuccess() {
        var success = AgentResult.success("ok", 1, Duration.ZERO, List.of());
        var failure = AgentResult.failure("err", 1, Duration.ZERO);
        assertTrue(success.success());
        assertFalse(success.isFailure());
        assertFalse(failure.success());
        assertTrue(failure.isFailure());
    }
}
