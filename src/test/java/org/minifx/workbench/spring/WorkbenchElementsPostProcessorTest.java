package org.minifx.workbench.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class WorkbenchElementsPostProcessorTest {

    private WorkbenchElementsPostProcessor pp;

    @Before
    public void setUp() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ExampleConfig.class);
        pp = ctx.getBean(WorkbenchElementsPostProcessor.class);

    }

    @Test
    public void publicIsPresent() {
        Optional<Method> m = pp.factoryMethodFor("public");
        assertThat(m.isPresent()).isTrue();
    }

    @Test
    public void staticIsPresent() {
        Optional<Method> m = pp.factoryMethodFor("static");
        assertThat(m.isPresent()).isTrue();
    }

    
    @Configuration
    public static class ExampleConfig {

        @Bean
        public static String staticString() {
            return "static";
        }

        @Bean
        public String publicString() {
            return "public";
        }

        @Bean
        public WorkbenchElementsPostProcessor postProcessor() {
            return new WorkbenchElementsPostProcessor();
        }
    }
}
