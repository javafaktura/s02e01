package io.github.javafaktura.s02e01.O3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.DayOfWeek;
import java.util.List;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@ContextConfiguration(classes = A_ContextComposition.AllDays.class)
class A_ContextComposition {

    @Autowired
    private List<DayOfWeek> allDays;

    @Test
    void shouldInjectBeansFromImportedConfigs() {
        assertTrue(
                allDays.containsAll(List.of(DayOfWeek.values())),
                "Not all days were injected. Expected entire week, got: " + allDays);
    }

    @Configuration
    static class Weekdays {
        @Bean DayOfWeek monday() {
            return MONDAY;
        }
        @Bean DayOfWeek tuesday() {
            return TUESDAY;
        }
        @Bean DayOfWeek wednesday() {
            return WEDNESDAY;
        }
        @Bean DayOfWeek thursday() {
            return THURSDAY;
        }
        @Bean DayOfWeek friday() {
            return FRIDAY;
        }
    }

    @Configuration
    static class WeekendDays {
        @Bean DayOfWeek saturday() {
            return SATURDAY;
        }
        @Bean DayOfWeek sunday() {
            return SUNDAY;
        }
    }

    /**
     * Technically, this class is unnecessary.
     * We could instantiate the context directly from both configurations.
     */
    @Configuration
    @Import({Weekdays.class, WeekendDays.class})
    static class AllDays { }
}
