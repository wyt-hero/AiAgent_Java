package com.aiagent.common.adr;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AdrRecord Tests")
class AdrRecordTest {

    @Nested
    @DisplayName("Factory methods")
    class FactoryTests {

        @Test
        @DisplayName("should_CreateWithTodayDate_When UsingOfWithoutDate")
        void should_CreateWithTodayDate_When_UsingOfWithoutDate() {
            AdrRecord adr = AdrRecord.of("ADR-0001", "Test Title",
                    AdrStatus.ACCEPTED, "Team", List.of("TASK-0001"));

            assertEquals("ADR-0001", adr.id());
            assertEquals("Test Title", adr.title());
            assertEquals(AdrStatus.ACCEPTED, adr.status());
            assertEquals(LocalDate.now(), adr.date());
            assertEquals("Team", adr.deciders());
            assertEquals(List.of("TASK-0001"), adr.references());
        }

        @Test
        @DisplayName("should_CreateWithExplicitDate_When UsingOfWithDate")
        void should_CreateWithExplicitDate_When_UsingOfWithDate() {
            LocalDate date = LocalDate.of(2026, 7, 23);
            AdrRecord adr = AdrRecord.of("ADR-0002", "Another ADR",
                    AdrStatus.PROPOSED, date, "Architect", List.of());

            assertEquals(date, adr.date());
            assertEquals(AdrStatus.PROPOSED, adr.status());
        }

        @Test
        @DisplayName("should_HandleNullReferences_When NullList")
        void should_HandleNullReferences_When_NullList() {
            AdrRecord adr = AdrRecord.of("ADR-0003", "No Refs",
                    AdrStatus.ACCEPTED, "Team", null);

            assertEquals(List.of(), adr.references());
        }
    }

    @Nested
    @DisplayName("Validation")
    class ValidationTests {

        @Test
        @DisplayName("should_ThrowWhenNullId")
        void should_ThrowWhenNullId() {
            assertThrows(NullPointerException.class, () ->
                    AdrRecord.of(null, "Title", AdrStatus.ACCEPTED, "Team", List.of()));
        }

        @Test
        @DisplayName("should_ThrowWhenBlankId")
        void should_ThrowWhenBlankId() {
            assertThrows(IllegalArgumentException.class, () ->
                    AdrRecord.of("  ", "Title", AdrStatus.ACCEPTED, "Team", List.of()));
        }

        @Test
        @DisplayName("should_ThrowWhenBlankTitle")
        void should_ThrowWhenBlankTitle() {
            assertThrows(IllegalArgumentException.class, () ->
                    AdrRecord.of("ADR-0001", "", AdrStatus.ACCEPTED, "Team", List.of()));
        }
    }

    @Nested
    @DisplayName("Query methods")
    class QueryTests {

        @Test
        @DisplayName("should_ReturnTrue_When StatusIsAccepted")
        void should_ReturnTrue_When_StatusIsAccepted() {
            AdrRecord adr = AdrRecord.of("ADR-0001", "Active",
                    AdrStatus.ACCEPTED, "Team", List.of());

            assertTrue(adr.isActive());
        }

        @Test
        @DisplayName("should_ReturnFalse_When StatusIsNotAccepted")
        void should_ReturnFalse_When_StatusIsNotAccepted() {
            AdrRecord adr = AdrRecord.of("ADR-0001", "Proposed",
                    AdrStatus.PROPOSED, "Team", List.of());

            assertFalse(adr.isActive());
        }
    }

    @Nested
    @DisplayName("withStatus()")
    class WithStatusTests {

        @Test
        @DisplayName("should_ReturnNewRecord_When StatusChanged")
        void should_ReturnNewRecord_When_StatusChanged() {
            AdrRecord original = AdrRecord.of("ADR-0001", "Test",
                    AdrStatus.PROPOSED, "Team", List.of());

            AdrRecord updated = original.withStatus(AdrStatus.ACCEPTED);

            assertEquals(AdrStatus.PROPOSED, original.status());
            assertEquals(AdrStatus.ACCEPTED, updated.status());
            assertEquals(original.id(), updated.id());
        }
    }

    @Nested
    @DisplayName("filename()")
    class FilenameTests {

        @Test
        @DisplayName("should_GenerateSlugifiedFilename")
        void should_GenerateSlugifiedFilename() {
            AdrRecord adr = AdrRecord.of("ADR-0001", "Adopt AIOS Governance Model",
                    AdrStatus.ACCEPTED, "Team", List.of());

            assertEquals("ADR-0001-adopt-aios-governance-model.md", adr.filename());
        }
    }
}
