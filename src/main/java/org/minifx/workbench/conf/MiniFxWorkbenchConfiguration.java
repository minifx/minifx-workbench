/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static java.util.stream.Collectors.toList;
import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;

import java.util.List;

import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.fxcommons.MiniFxWorkbench;
import org.minifx.workbench.components.MainPane;
import org.minifx.workbench.nodes.FxNodeFactory;
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

import javafx.scene.control.MenuBar;

@Configuration
@Import({ FactoryMethodsCollectorConfiguration.class, FxmlNodeServiceConfiguration.class,
        NodeFactoryConfiguration.class, MiniFxWorkbenchInitialization.class })
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
    public MiniFxWorkbench miniFxContext(MainPane mainPane, ApplicationContext ctx) {
        if (sceneBuilder == null) {
            sceneBuilder = miniFxSceneBuilder().withSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }

        if (cssStyleSheets != null) {
            sceneBuilder.withAdditionalCss(cssStyleSheets.stream().flatMap(List::stream).collect(toList()));
        }

        return new MiniFxWorkbench(sceneBuilder, mainPane, ctx);
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
            FxNodeFactory fxNodeFactory, @Autowired(required=false) MenuBar menuBar) {
        MainPane mainPanel = new MainPane(factoryMethodsRepository.toolbarItems(), publisher, fxNodeFactory);
        mainPanel.setId(ID_MAIN_PANEL);
        return mainPanel;
    }

}
