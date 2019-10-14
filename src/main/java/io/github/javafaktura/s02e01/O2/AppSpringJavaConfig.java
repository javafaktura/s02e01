package io.github.javafaktura.s02e01.O2;

import io.github.javafaktura.s02e01.logic.SomeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class AppSpringJavaConfig {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(ServiceConfig.class);

        var service = ctx.getBean(SomeService.class);

        service.mostPopularValue()
                .ifPresent(System.out::println);
    }
}
