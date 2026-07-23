package com.aiagent.common.exception;
import java.util.Map;
public class McpException extends BaseException {
    public McpException(ErrorCode errorCode) { super(errorCode); }
    public McpException(ErrorCode errorCode, String message) { super(errorCode, message); }
    public McpException(ErrorCode errorCode, Throwable cause) { super(errorCode, cause); }
    public McpException(ErrorCode errorCode, String message, Throwable cause) { super(errorCode, message, cause); }
    public McpException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) { super(errorCode, message, cause, context); }
    public static McpException connectionFailed(String serverUrl, Throwable cause) { return new McpException(CommonErrorCode.MCP_CONNECTION_FAILED, CommonErrorCode.MCP_CONNECTION_FAILED.message().formatted(serverUrl), cause); }
    public static McpException protocolError(String detail, Throwable cause) { return new McpException(CommonErrorCode.MCP_PROTOCOL_ERROR, CommonErrorCode.MCP_PROTOCOL_ERROR.message().formatted(detail), cause); }
}