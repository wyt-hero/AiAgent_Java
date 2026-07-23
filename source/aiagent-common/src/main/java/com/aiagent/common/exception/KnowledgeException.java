package com.aiagent.common.exception;
import java.util.Map;
public class KnowledgeException extends BaseException {
    public KnowledgeException(ErrorCode errorCode) { super(errorCode); }
    public KnowledgeException(ErrorCode errorCode, String message) { super(errorCode, message); }
    public KnowledgeException(ErrorCode errorCode, Throwable cause) { super(errorCode, cause); }
    public KnowledgeException(ErrorCode errorCode, String message, Throwable cause) { super(errorCode, message, cause); }
    public KnowledgeException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) { super(errorCode, message, cause, context); }
    public static KnowledgeException indexFailed(String detail, Throwable cause) { return new KnowledgeException(CommonErrorCode.KNOWLEDGE_INDEX_FAILED, CommonErrorCode.KNOWLEDGE_INDEX_FAILED.message().formatted(detail), cause); }
    public static KnowledgeException retrievalFailed(String detail, Throwable cause) { return new KnowledgeException(CommonErrorCode.KNOWLEDGE_RETRIEVAL_FAILED, CommonErrorCode.KNOWLEDGE_RETRIEVAL_FAILED.message().formatted(detail), cause); }
}