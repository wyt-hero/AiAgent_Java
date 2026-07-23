package com.aiagent.common.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates code style rules programmatically.
 *
 * <p>Provides static methods to check common style violations
 * defined in CODING_STYLE.md and JAVA_RULES.md.
 *
 * <p>Example:
 * <pre>{@code
 * StyleReport report = CodeStyleValidator.validate("MyFile.java", sourceCode);
 * if (report.hasViolations()) {
 *     report.violations().forEach(v -> log.warn("Style: {}", v));
 * }
 * }</pre>
 */
public final class CodeStyleValidator {

    private static final int MAX_LINE_LENGTH = 120;
    private static final Pattern TAB_PATTERN = Pattern.compile("\t");
    private static final Pattern TRAILING_WHITESPACE_PATTERN = Pattern.compile("[ \\t]+$");
    private static final Pattern WILDCARD_IMPORT_PATTERN = Pattern.compile("^import\\s+[\\w.]+\\.\\*;", Pattern.MULTILINE);
    private static final Pattern SYSTEM_OUT_PATTERN = Pattern.compile("System\\.(out|err)\\.(print|println)");

    private CodeStyleValidator() {
        throw new AssertionError("Utility class — not instantiable");
    }

    /**
     * Validate source code against style rules.
     *
     * @param filename   the file name (for reporting)
     * @param sourceCode the source code content
     * @return a StyleReport containing all violations found
     */
    public static StyleReport validate(String filename, String sourceCode) {
        Objects.requireNonNull(filename, "filename must not be null");
        Objects.requireNonNull(sourceCode, "sourceCode must not be null");

        List<String> violations = new ArrayList<>();

        String[] lines = sourceCode.split("\n", -1);

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int lineNum = i + 1;

            // Check line length
            if (line.length() > MAX_LINE_LENGTH) {
                violations.add("%s:%d — Line exceeds %d characters (%d)".formatted(
                        filename, lineNum, MAX_LINE_LENGTH, line.length()));
            }

            // Check for tabs
            if (TAB_PATTERN.matcher(line).find()) {
                violations.add("%s:%d — Tab character found (use spaces)".formatted(filename, lineNum));
            }

            // Check trailing whitespace
            if (TRAILING_WHITESPACE_PATTERN.matcher(line).find()) {
                violations.add("%s:%d — Trailing whitespace".formatted(filename, lineNum));
            }
        }

        // Check wildcard imports
        if (WILDCARD_IMPORT_PATTERN.matcher(sourceCode).find()) {
            violations.add("%s — Wildcard import found (use explicit imports)".formatted(filename));
        }

        // Check System.out/err usage
        if (SYSTEM_OUT_PATTERN.matcher(sourceCode).find()) {
            violations.add("%s — System.out/err usage (use SLF4J logger)".formatted(filename));
        }

        // Check final newline
        if (!sourceCode.isEmpty() && !sourceCode.endsWith("\n")) {
            violations.add("%s — Missing final newline".formatted(filename));
        }

        return new StyleReport(filename, List.copyOf(violations));
    }

    /**
     * Check if a class name follows PascalCase convention.
     */
    public static boolean isPascalCase(String name) {
        return name != null && !name.isEmpty()
                && Character.isUpperCase(name.charAt(0))
                && !name.contains("_");
    }

    /**
     * Check if a method/variable name follows camelCase convention.
     */
    public static boolean isCamelCase(String name) {
        return name != null && !name.isEmpty()
                && Character.isLowerCase(name.charAt(0))
                && !name.contains("_")
                && !name.contains("-");
    }

    /**
     * Check if a constant name follows UPPER_SNAKE_CASE convention.
     */
    public static boolean isUpperSnakeCase(String name) {
        return name != null && !name.isEmpty()
                && name.equals(name.toUpperCase())
                && !name.contains(" ")
                && !name.contains("-");
    }

    /**
     * Style validation report.
     *
     * @param filename   the validated file name
     * @param violations list of violation descriptions
     */
    public record StyleReport(
            String filename,
            List<String> violations
    ) {
        public StyleReport {
            Objects.requireNonNull(filename, "filename must not be null");
            Objects.requireNonNull(violations, "violations must not be null");
            violations = List.copyOf(violations);
        }

        /**
         * Whether any violations were found.
         */
        public boolean hasViolations() {
            return !violations.isEmpty();
        }

        /**
         * Number of violations found.
         */
        public int violationCount() {
            return violations.size();
        }
    }
}
