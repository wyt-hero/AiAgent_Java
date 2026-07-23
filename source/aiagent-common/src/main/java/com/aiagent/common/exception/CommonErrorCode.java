package com.aiagent.common.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter @AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INTERNAL_ERROR(10001,"Internal server error"),
    INVALID_ARGUMENT(10002,"Invalid argument: %s"),
    NOT_FOUND(10003,"Resource not found: %s"),
    ALREADY_EXISTS(10004,"Resource already exists: %s"),
    OPERATION_NOT_ALLOWED(10005,"Operation not allowed: %s"),
    TIMEOUT(10006,"Operation timed out after %d ms"),
    RATE_LIMIT_EXCEEDED(10007,"Rate limit exceeded"),
    UNAUTHORIZED(20001,"Authentication required"),
    FORBIDDEN(20002,"Access denied: %s"),
    TOKEN_EXPIRED(20003,"Token has expired"),
    TOKEN_INVALID(20004,"Invalid token"),
    AGENT_EXECUTION_FAILED(30001,"Agent execution failed: %s"),
    AGENT_NOT_FOUND(30002,"Agent not found: %s"),
    AGENT_TIMEOUT(30003,"Agent execution timed out"),
    AGENT_STATE_INVALID(30004,"Invalid agent state: %s"),
    TOOL_NOT_FOUND(40001,"Tool not found: %s"),
    TOOL_EXECUTION_FAILED(40002,"Tool execution failed: %s"),
    TOOL_PARAM_INVALID(40003,"Invalid tool parameter: %s"),
    TOOL_PERMISSION_DENIED(40004,"Tool permission denied: %s"),
    PROMPT_RENDER_FAILED(50001,"Prompt rendering failed: %s"),
    PROMPT_TOKEN_EXCEEDED(50002,"Token limit exceeded: max=%d, actual=%d"),
    MEMORY_READ_FAILED(60001,"Memory read failed: %s"),
    MEMORY_WRITE_FAILED(60002,"Memory write failed: %s"),
    MEMORY_CONSOLIDATION_FAILED(60003,"Memory consolidation failed: %s"),
    WORKFLOW_EXECUTION_FAILED(70001,"Workflow execution failed: %s"),
    WORKFLOW_NODE_FAILED(70002,"Workflow node failed: %s"),
    WORKFLOW_CYCLE_DETECTED(70003,"Workflow cycle detected"),
    MCP_CONNECTION_FAILED(80001,"MCP connection failed: %s"),
    MCP_PROTOCOL_ERROR(80002,"MCP protocol error: %s"),
    KNOWLEDGE_INDEX_FAILED(90001,"Knowledge indexing failed: %s"),
    KNOWLEDGE_RETRIEVAL_FAILED(90002,"Knowledge retrieval failed: %s");
    private final int code;
    private final String message;
    @Override public int httpStatus() {
        return switch(this) {
            case UNAUTHORIZED -> 401;
            case FORBIDDEN, TOOL_PERMISSION_DENIED -> 403;
            case NOT_FOUND, AGENT_NOT_FOUND, TOOL_NOT_FOUND -> 404;
            case ALREADY_EXISTS -> 409;
            case RATE_LIMIT_EXCEEDED -> 429;
            case INVALID_ARGUMENT, TOOL_PARAM_INVALID -> 400;
            default -> 500;
        };
    }
}