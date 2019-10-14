package io.github.javafaktura.s02e01.O4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
class A_BeanScopes {

    @Test
    void shouldReuseSameInstanceOfSingleton() {
        var ctx = new AnnotationConfigApplicationContext(A_BeanScopes.class);
        assertSame(
                ctx.getBean(MySingleton.class),
                ctx.getBean(MySingleton.class));
    }

    @Test
    void shouldCreateNewInstancesOfPrototypes() {
        var ctx = new AnnotationConfigApplicationContext(A_BeanScopes.class);
        assertNotSame(
                ctx.getBean(MyPrototype.class),
                ctx.getBean(MyPrototype.class));
    }

    @Test
    void shouldInjectPrototypesToSingleton() {
        var ctx = new AnnotationConfigApplicationContext(A_BeanScopes.class);
        MySingleton singleton = ctx.getBean(MySingleton.class);
        assertNotSame(
                singleton.proto(),
                singleton.proto());
    }

    @Component
    @Scope(SCOPE_PROTOTYPE)
    private static class MyPrototype {
    }

    @Component
    static abstract class MySingleton {
        @Lookup
        abstract MyPrototype proto();
    }
}
