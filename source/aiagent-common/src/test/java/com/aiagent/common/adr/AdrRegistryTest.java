package com.aiagent.common.adr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AdrRegistry Tests")
class AdrRegistryTest {

    private AdrRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new AdrRegistry();
    }

    @Nested
    @DisplayName("register()")
    class RegisterTests {

        @Test
        @DisplayName("should_AddAdr_When ValidRecord")
        void should_AddAdr_When_ValidRecord() {
            AdrRecord adr = AdrRecord.of("ADR-0001", "Test",
                    AdrStatus.ACCEPTED, "Team", List.of());

            registry.register(adr);

            assertEquals(1, registry.size());
            assertFalse(registry.isEmpty());
        }

        @Test
        @DisplayName("should_Throw_When DuplicateId")
        void should_Throw_When_DuplicateId() {
            AdrRecord adr1 = AdrRecord.of("ADR-0001", "First",
                    AdrStatus.ACCEPTED, "Team", List.of());
            AdrRecord adr2 = AdrRecord.of("ADR-0001", "Second",
                    AdrStatus.PROPOSED, "Team", List.of());

            registry.register(adr1);

            assertThrows(IllegalArgumentException.class, () ->
                    registry.register(adr2));
        }
    }

    @Nested
    @DisplayName("findById()")
    class FindByIdTests {

        @Test
        @DisplayName("should_ReturnFound_When Exists")
        void should_ReturnFound_When_Exists() {
            AdrRecord adr = AdrRecord.of("ADR-0001", "Test",
                    AdrStatus.ACCEPTED, "Team", List.of());
            registry.register(adr);

            var found = registry.findById("ADR-0001");

            assertTrue(found.isPresent());
            assertEquals("Test", found.get().title());
        }

        @Test
        @DisplayName("should_ReturnEmpty_When NotExists")
        void should_ReturnEmpty_When_NotExists() {
            assertTrue(registry.findById("ADR-9999").isEmpty());
        }
    }

    @Nested
    @DisplayName("findByStatus()")
    class FindByStatusTests {

        @Test
        @DisplayName("should_ReturnMatching_When StatusMatches")
        void should_ReturnMatching_When_StatusMatches() {
            registry.register(AdrRecord.of("ADR-0001", "A1",
                    AdrStatus.ACCEPTED, "Team", List.of()));
            registry.register(AdrRecord.of("ADR-0002", "A2",
                    AdrStatus.PROPOSED, "Team", List.of()));
            registry.register(AdrRecord.of("ADR-0003", "A3",
                    AdrStatus.ACCEPTED, "Team", List.of()));

            List<AdrRecord> accepted = registry.findByStatus(AdrStatus.ACCEPTED);
            List<AdrRecord> proposed = registry.findByStatus(AdrStatus.PROPOSED);

            assertEquals(2, accepted.size());
            assertEquals(1, proposed.size());
        }
    }

    @Nested
    @DisplayName("active()")
    class ActiveTests {

        @Test
        @DisplayName("should_ReturnOnlyAccepted_When MixedStatuses")
        void should_ReturnOnlyAccepted_When_MixedStatuses() {
            registry.register(AdrRecord.of("ADR-0001", "A1",
                    AdrStatus.ACCEPTED, "Team", List.of()));
            registry.register(AdrRecord.of("ADR-0002", "A2",
                    AdrStatus.DEPRECATED, "Team", List.of()));

            assertEquals(1, registry.active().size());
        }
    }

    @Nested
    @DisplayName("all()")
    class AllTests {

        @Test
        @DisplayName("should_ReturnSortedById")
        void should_ReturnSortedById() {
            registry.register(AdrRecord.of("ADR-0003", "C",
                    AdrStatus.ACCEPTED, "Team", List.of()));
            registry.register(AdrRecord.of("ADR-0001", "A",
                    AdrStatus.ACCEPTED, "Team", List.of()));
            registry.register(AdrRecord.of("ADR-0002", "B",
                    AdrStatus.ACCEPTED, "Team", List.of()));

            List<AdrRecord> all = registry.all();

            assertEquals("ADR-0001", all.get(0).id());
            assertEquals("ADR-0002", all.get(1).id());
            assertEquals("ADR-0003", all.get(2).id());
        }
    }

    @Nested
    @DisplayName("updateStatus()")
    class UpdateStatusTests {

        @Test
        @DisplayName("should_UpdateStatus_When Exists")
        void should_UpdateStatus_When_Exists() {
            registry.register(AdrRecord.of("ADR-0001", "Test",
                    AdrStatus.PROPOSED, "Team", List.of()));

            AdrRecord updated = registry.updateStatus("ADR-0001", AdrStatus.ACCEPTED);

            assertEquals(AdrStatus.ACCEPTED, updated.status());
            assertEquals(AdrStatus.ACCEPTED, registry.findById("ADR-0001").get().status());
        }

        @Test
        @DisplayName("should_Throw_When NotExists")
        void should_Throw_When_NotExists() {
            assertThrows(IllegalArgumentException.class, () ->
                    registry.updateStatus("ADR-9999", AdrStatus.ACCEPTED));
        }
    }

    @Nested
    @DisplayName("isEmpty()")
    class IsEmptyTests {

        @Test
        @DisplayName("should_ReturnTrue_When NoRecords")
        void should_ReturnTrue_When_NoRecords() {
            assertTrue(registry.isEmpty());
        }

        @Test
        @DisplayName("should_ReturnFalse_When HasRecords")
        void should_ReturnFalse_When_HasRecords() {
            registry.register(AdrRecord.of("ADR-0001", "Test",
                    AdrStatus.ACCEPTED, "Team", List.of()));

            assertFalse(registry.isEmpty());
        }
    }
}
