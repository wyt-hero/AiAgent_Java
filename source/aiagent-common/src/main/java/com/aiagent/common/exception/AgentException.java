package com.aiagent.common.exception;
import java.util.Map;
public class AgentException extends BaseException {
    public AgentException(ErrorCode errorCode) { super(errorCode); }
    public AgentException(ErrorCode errorCode, String message) { super(errorCode, message); }
    public AgentException(ErrorCode errorCode, Throwable cause) { super(errorCode, cause); }
    public AgentException(ErrorCode errorCode, String message, Throwable cause) { super(errorCode, message, cause); }
    public AgentException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) { super(errorCode, message, cause, context); }
    public static AgentException executionFailed(String detail, Throwable cause) { return new AgentException(CommonErrorCode.AGENT_EXECUTION_FAILED, CommonErrorCode.AGENT_EXECUTION_FAILED.message().formatted(detail), cause); }
    public static AgentException notFound(String agentId) { return new AgentException(CommonErrorCode.AGENT_NOT_FOUND, CommonErrorCode.AGENT_NOT_FOUND.message().formatted(agentId)); }
    public static AgentException timeout() { return new AgentException(CommonErrorCode.AGENT_TIMEOUT); }
    public static AgentException invalidState(String state) { return new AgentException(CommonErrorCode.AGENT_STATE_INVALID, CommonErrorCode.AGENT_STATE_INVALID.message().formatted(state)); }
}