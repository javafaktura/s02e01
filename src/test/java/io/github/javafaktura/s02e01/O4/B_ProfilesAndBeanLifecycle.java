package io.github.javafaktura.s02e01.O4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.PostConstruct;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan
@SpringJUnitConfig
@ContextConfiguration(classes = B_ProfilesAndBeanLifecycle.class)
@ActiveProfiles("test")
class B_ProfilesAndBeanLifecycle {

    @Autowired
    private BeanWithPostConstruct withPostConstruct;

    @Autowired
    private CtxAwareBean ctxAware;

    @Autowired
    private ApplicationContext ctx;

    @Test
    void shouldCallBeanLifecycleMethods() {
        assertTrue(withPostConstruct.isInitialized());
    }

    @Test
    void shouldInjectApplicationContext() {
        assertNotNull(ctxAware.ctx, "Context not injected");
    }

    @Test
    void shouldntCreateBeansForInactiveProfile() {
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ctx.getBean(Instant.class),
                "Profile annotation has been ignored by ApplicationContext");
    }

    @Component
    private static class BeanWithPostConstruct {
        private final String value;
        private boolean initialized;

        private BeanWithPostConstruct(String value) {
            this.value = value;
            this.initialized = false;
        }

        // same as InitializingBean#afterPropertiesSet
        @PostConstruct
        void init() {
            initialized = true;
            assertEquals(
                    "val", value,
                    "Value not injected");
        }

        boolean isInitialized() {
            return initialized;
        }
    }

    @Bean
    String value() {
        return "val";
    }

    @Component
    private static class CtxAwareBean implements ApplicationContextAware {
        private ApplicationContext ctx;

        @Override
        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            this.ctx = ctx;
        }

        @PostConstruct
        void init() {
            assertNotNull(ctx, "Context not injected");
        }
    }

    @Bean
    @Profile("!test")
    Instant notInTest() {
        return Instant.now();
    }
}
