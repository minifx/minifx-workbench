package org.minifx.workbench.conf;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.minifx.workbench.components.MainPane;
import org.minifx.workbench.domain.definition.FooterDefinition;
import org.minifx.workbench.domain.definition.PerspectiveDefinition;
import org.minifx.workbench.providers.PerspectiveProvider;
import org.minifx.workbench.spring.ActivatePerspectiveCommand;
import org.minifx.workbench.spring.ElementsDefinitionConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class MiniFxWorkbenchInitialization {

    @EventListener
    public void perspectivesAndFootersInitialization(ContextRefreshedEvent evt) {
        ApplicationContext ctx = evt.getApplicationContext();

        List<PerspectiveDefinition> allPerspectives = allPerspectives(ctx);

        MainPane mainPane = ctx.getBean(MainPane.class);
        mainPane.setPerspectives(allPerspectives);
        mainPane.setFooters(allFooters(ctx));

        /* By default set first perspective active */
        allPerspectives.stream() //
                .min(comparing(def -> def.displayProperties().order())) //
                .map(PerspectiveDefinition::perspective)
                .ifPresent(perspective -> ctx.publishEvent(new ActivatePerspectiveCommand(perspective)));
    }

    private static Collection<FooterDefinition> allFooters(ApplicationContext ctx) {
        return ctx.getBean(ElementsDefinitionConstructor.class).footers();
    }

//    private static Collection<ToolbarItemDefinition> allToolbarItems(ApplicationContext ctx) {
//        return ctx.getBean(ElementsDefinitionConstructor.class).toolbarItems();
//    }

    private static List<PerspectiveDefinition> allPerspectives(ApplicationContext ctx) {
        ElementsDefinitionConstructor instantiator = ctx.getBean(ElementsDefinitionConstructor.class);
        Stream<PerspectiveDefinition> perspectivesFound = instantiator.perspectives().stream();
        Stream<PerspectiveDefinition> providedPerspectives = providedPerspectives(ctx).stream();
        return concat(perspectivesFound, providedPerspectives).collect(toList());
    }

    private static Collection<PerspectiveDefinition> providedPerspectives(ApplicationContext ctx) {
        return ctx.getBeansOfType(PerspectiveProvider.class).values().stream() //
                .flatMap(provider -> provider.perspectives().stream()) //
                .collect(toList());
    }

}
