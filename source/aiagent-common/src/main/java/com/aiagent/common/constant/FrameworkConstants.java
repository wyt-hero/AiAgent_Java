package com.aiagent.common.constant;

/**
 * Framework-wide constants.
 */
public final class FrameworkConstants {

    private FrameworkConstants() {
        // Constants class
    }

    /**
     * Framework name.
     */
    public static final String FRAMEWORK_NAME = "AiAgent-Java";

    /**
     * Framework version.
     */
    public static final String FRAMEWORK_VERSION = "0.1.0-SNAPSHOT";

    /**
     * Default page size.
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Maximum page size.
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * Default timeout in milliseconds.
     */
    public static final long DEFAULT_TIMEOUT_MS = 30_000L;

    /**
     * Maximum retry count.
     */
    public static final int MAX_RETRY_COUNT = 3;

    /**
     * Date/time format patterns.
     */
    public static final class DateTimeFormat {
        public static final String ISO_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        public static final String SIMPLE = "yyyy-MM-dd HH:mm:ss";

        private DateTimeFormat() {
        }
    }

    /**
     * Header names used across the framework.
     */
    public static final class Headers {
        public static final String REQUEST_ID = "X-Request-Id";
        public static final String AGENT_ID = "X-Agent-Id";
        public static final String SESSION_ID = "X-Session-Id";
        public static final String TRACE_ID = "X-Trace-Id";

        private Headers() {
        }
    }
}
