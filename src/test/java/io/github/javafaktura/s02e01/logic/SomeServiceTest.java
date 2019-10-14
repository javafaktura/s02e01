package io.github.javafaktura.s02e01.logic;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Thanks to DI, I can easily test my business logic.
 */
class SomeServiceTest {

    @Test
    void shouldReturnMostPopularValue() {
        SomeRepository repo = () -> List.of("foo", "bar", "foo");

        assertEquals(Optional.of("foo"),
                new SomeService(repo).mostPopularValue(),
                "Most popular value was not extracted");
    }

    @Test
    void shouldReturnEmptyOptionalIfNoDataFound() {
        SomeRepository repo = List::of;

        assertFalse(
                new SomeService(repo).mostPopularValue().isPresent(),
                "Result should be empty");
    }
}
