package io.github.javafaktura.s02e01.O3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class B_ContextHierarchy {

    @Test
    void shouldContainBeansDefinedInThisContext() {
        var ctx = new AnnotationConfigApplicationContext(Parent.class);
        assertEquals("parent", ctx.getBean("parent"));

        // BTW, configuration is also a bean!
        assertNotNull(ctx.getBean(Parent.class));
    }

    @Test
    void shouldInheritBeansFromParentContext() {
        var parentCtx = new AnnotationConfigApplicationContext(Parent.class);
        var childCtx = new AnnotationConfigApplicationContext(AlphaChild.class);
        childCtx.setParent(parentCtx);

        assertEquals("parent", childCtx.getBean("parent"));
        assertEquals("alpha", childCtx.getBean("alpha"));
    }

    @Test
    void shouldntContainBeansFromSiblingContexts() {
        var parent = new AnnotationConfigApplicationContext(Parent.class);

        var childA = new AnnotationConfigApplicationContext(AlphaChild.class);
        childA.setParent(parent);

        var childB = new AnnotationConfigApplicationContext(BravoChild.class);
        childB.setParent(parent);

        assertEquals("parent", childB.getBean("parent"));
        assertEquals("bravo", childB.getBean("bravo"));

        assertThrows(NoSuchBeanDefinitionException.class, () -> childB.getBean("alpha"));
    }

    private static class Parent {
        @Bean String parent() {
            return "parent";
        }
    }

    private static class AlphaChild {
        @Bean String alpha() {
            return "alpha";
        }
    }

    private static class BravoChild {
        @Bean String bravo() {
            return "bravo";
        }
    }
}
