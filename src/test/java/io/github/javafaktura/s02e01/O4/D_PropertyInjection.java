package io.github.javafaktura.s02e01.O4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.WEDNESDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@ContextConfiguration(classes = D_PropertyInjection.Config.class)
class D_PropertyInjection {

    @Autowired
    ApplicationContext ctx;

    @Test
    void shouldInjectPropertiesToConfig() {
        var config = ctx.getBean(Config.class);

        assertEquals("DEFAULT", config.getStringWithDefault());
        assertEquals(42, config.getInvValue());
        assertEquals(WEDNESDAY, config.getDayOfWeek());
        assertTrue(config.isSpel());
    }

    @Configuration
    @PropertySource("classpath:typeconversion.properties")
    static class Config {
        @Value("${withDefault:DEFAULT}")
        private String stringWithDefault;
        @Value("${intValue}")
        private int invValue;
        @Value("${dayOfWeek}")
        private DayOfWeek dayOfWeek;
        @Value("#{'${spel}'.split(',').length > 2}")
        private boolean spel;

        String getStringWithDefault() {
            return stringWithDefault;
        }

        int getInvValue() {
            return invValue;
        }

        DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        boolean isSpel() {
            return spel;
        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propsPlaceholderConfig() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}
