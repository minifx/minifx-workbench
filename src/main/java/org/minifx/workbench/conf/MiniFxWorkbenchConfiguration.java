/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import javafx.scene.Scene;
import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.components.MainPane;
import org.minifx.workbench.domain.definition.FooterDefinition;
import org.minifx.workbench.domain.definition.PerspectiveDefinition;
import org.minifx.workbench.nodes.FxNodeFactory;
import org.minifx.workbench.providers.PerspectiveProvider;
import org.minifx.workbench.spring.BeanInformationExtractor;
import org.minifx.workbench.spring.BeanInformationExtractorImpl;
import org.minifx.workbench.spring.BeanInformationRepository;
import org.minifx.workbench.spring.ElementsDefinitionConstructor;
import org.minifx.workbench.spring.WorkbenchElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;

@Configuration
@Import({FactoryMethodsCollectorConfiguration.class, FxmlNodeServiceConfiguration.class,
        NodeFactoryConfiguration.class, MiniFxWorkbenchInitialization.class})
public class MiniFxWorkbenchConfiguration {

    private static final int DEFAULT_HEIGHT = 760;
    private static final int DEFAULT_WIDTH = 1280;

    public static final String ID_MAIN_PANEL = "minifx-workbench-main-panel";

    @Autowired(required = false)
    @Qualifier("cssStyleSheets")
    private List<List<String>> cssStyleSheets;

    @Autowired(required = false)
    private MiniFxSceneBuilder sceneBuilder;

    @Bean
    public Scene mainScene(MainPane mainPane) {
        if (sceneBuilder == null) {
            sceneBuilder = miniFxSceneBuilder().withSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }

        sceneBuilder.withRoot(mainPane);

        if (cssStyleSheets != null) {
            sceneBuilder.withAdditionalCss(cssStyleSheets.stream().flatMap(List::stream).collect(toList()));
        }

        return sceneBuilder.build();
    }

    @Bean
    public ElementsDefinitionConstructor elementsDefinitionConstructor(
            WorkbenchElementsRepository factoryMethodsRepository, BeanInformationExtractor beanInformationExtractor,
            FxNodeFactory fxNodeFactory) {
        return new ElementsDefinitionConstructor(factoryMethodsRepository, beanInformationExtractor, fxNodeFactory);
    }

    @Bean
    public BeanInformationExtractor beanInformationExtractor(BeanInformationRepository factoryMethodsRepository) {
        return new BeanInformationExtractorImpl(factoryMethodsRepository);
    }

    @Bean
    public MainPane mainPane(ApplicationEventPublisher publisher, WorkbenchElementsRepository factoryMethodsRepository,
            FxNodeFactory fxNodeFactory) {
        MainPane mainPanel = new MainPane(factoryMethodsRepository.toolbarItems(), publisher, fxNodeFactory);
        mainPanel.setId(ID_MAIN_PANEL);
        return mainPanel;
    }

}
