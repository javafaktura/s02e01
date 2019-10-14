package io.github.javafaktura.s02e01.O4;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@ContextConfiguration(classes = C_ApplicationEvents.Config.class)
class C_ApplicationEvents {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private MyEventListener listener;
    @Autowired
    private ApplicationContext context;

    @Test
    void shouldCollectPublishedEvents() {
        eventPublisher.publishEvent(new MyEvent(context));

        assertTrue(
                listener.collector().contains(ContextRefreshedEvent.class),
                "Listener didn't collect expected event " + ContextRefreshedEvent.class);

        assertTrue(
                listener.collector().contains(MyEvent.class),
                "Listener didn't collect expected event " + MyEvent.class);
    }

    private static class MyEvent extends ApplicationContextEvent {
        MyEvent(ApplicationContext source) {
            super(source);
        }
    }

    private static class MyEventListener implements ApplicationListener<ApplicationContextEvent> {

        private static final Logger LOG = LoggerFactory.getLogger(MyEventListener.class);

        private List<Class<? extends ApplicationContextEvent>> eventsCollector = new ArrayList<>();

        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            LOG.info("Received event {}", event);
            eventsCollector.add(event.getClass());
        }

        List<Class<? extends ApplicationContextEvent>> collector() {
            return eventsCollector;
        }
    }

    @Configuration
    static class Config {

        @Bean
        MyEventListener listener() {
            return new MyEventListener();
        }
    }
}
