package com.aiagent.common.style;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CodeStyleValidator Tests")
class CodeStyleValidatorTest {

    @Nested
    @DisplayName("validate()")
    class ValidateTests {

        @Test
        @DisplayName("should_ReturnNoViolations_When CleanCode")
        void should_ReturnNoViolations_When_CleanCode() {
            String code = "package com.aiagent;\n\nimport java.util.List;\n\npublic class MyClass {\n}\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("MyClass.java", code);

            assertFalse(report.hasViolations());
            assertEquals(0, report.violationCount());
        }

        @Test
        @DisplayName("should_DetectLongLines_When Exceeds120Chars")
        void should_DetectLongLines_When_Exceeds120Chars() {
            String longLine = "    String x = \"" + "a".repeat(120) + "\";\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", longLine);

            assertTrue(report.hasViolations());
            assertTrue(report.violations().get(0).contains("Line exceeds"));
        }

        @Test
        @DisplayName("should_DetectTabs_When TabCharacters")
        void should_DetectTabs_When_TabCharacters() {
            String code = "\tpublic void test() {}\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", code);

            assertTrue(report.hasViolations());
            assertTrue(report.violations().get(0).contains("Tab character"));
        }

        @Test
        @DisplayName("should_DetectTrailingWhitespace_When SpacesAtEnd")
        void should_DetectTrailingWhitespace_When_SpacesAtEnd() {
            String code = "public class Test {   \n}\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", code);

            assertTrue(report.hasViolations());
            assertTrue(report.violations().get(0).contains("Trailing whitespace"));
        }

        @Test
        @DisplayName("should_DetectWildcardImports_When StarImport")
        void should_DetectWildcardImports_When_StarImport() {
            String code = "import java.util.*;\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", code);

            assertTrue(report.hasViolations());
            assertTrue(report.violations().stream().anyMatch(v -> v.contains("Wildcard import")));
        }

        @Test
        @DisplayName("should_DetectSystemOut_When SystemOutPrintln")
        void should_DetectSystemOut_When_SystemOutPrintln() {
            String code = "public class Test {\n    void run() { System.out.println(\"hi\"); }\n}\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", code);

            assertTrue(report.hasViolations());
            assertTrue(report.violations().stream().anyMatch(v -> v.contains("System.out/err")));
        }

        @Test
        @DisplayName("should_DetectMissingNewline_When NoFinalNewline")
        void should_DetectMissingNewline_When_NoFinalNewline() {
            String code = "public class Test {}";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", code);

            assertTrue(report.hasViolations());
            assertTrue(report.violations().stream().anyMatch(v -> v.contains("final newline")));
        }

        @Test
        @DisplayName("should_ReturnImmutableViolations_When Accessed")
        void should_ReturnImmutableViolations_When_Accessed() {
            String code = "\tbad\n";
            CodeStyleValidator.StyleReport report = CodeStyleValidator.validate("Test.java", code);

            assertThrows(UnsupportedOperationException.class, () ->
                    report.violations().add("hack"));
        }
    }

    @Nested
    @DisplayName("Naming conventions")
    class NamingTests {

        @Test
        @DisplayName("should_ReturnTrue_When PascalCase")
        void should_ReturnTrue_When_PascalCase() {
            assertTrue(CodeStyleValidator.isPascalCase("AgentKernel"));
            assertTrue(CodeStyleValidator.isPascalCase("MyClass"));
            assertFalse(CodeStyleValidator.isPascalCase("agentKernel"));
            assertFalse(CodeStyleValidator.isPascalCase("MY_CLASS"));
            assertFalse(CodeStyleValidator.isPascalCase(null));
            assertFalse(CodeStyleValidator.isPascalCase(""));
        }

        @Test
        @DisplayName("should_ReturnTrue_When CamelCase")
        void should_ReturnTrue_When_CamelCase() {
            assertTrue(CodeStyleValidator.isCamelCase("agentKernel"));
            assertTrue(CodeStyleValidator.isCamelCase("myVariable"));
            assertFalse(CodeStyleValidator.isCamelCase("AgentKernel"));
            assertFalse(CodeStyleValidator.isCamelCase("my_variable"));
            assertFalse(CodeStyleValidator.isCamelCase(null));
        }

        @Test
        @DisplayName("should_ReturnTrue_When UpperSnakeCase")
        void should_ReturnTrue_When_UpperSnakeCase() {
            assertTrue(CodeStyleValidator.isUpperSnakeCase("MAX_COUNT"));
            assertTrue(CodeStyleValidator.isUpperSnakeCase("DEFAULT_VALUE"));
            assertFalse(CodeStyleValidator.isUpperSnakeCase("maxCount"));
            assertFalse(CodeStyleValidator.isUpperSnakeCase("my-value"));
            assertFalse(CodeStyleValidator.isUpperSnakeCase(null));
        }
    }
}
