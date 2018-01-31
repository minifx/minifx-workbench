/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;
import static org.minifx.workbench.util.MoreCollections.emptyIfNull;

import java.util.List;

import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.components.MainPane;
import org.minifx.workbench.domain.definition.PerspectiveDefinition;
import org.minifx.workbench.nodes.FxNodeFactory;
import org.minifx.workbench.providers.PerspectiveProvider;
import org.minifx.workbench.spring.BeanInformationExtractor;
import org.minifx.workbench.spring.BeanInformationRepository;
import org.minifx.workbench.spring.BeanInormationExtractorImpl;
import org.minifx.workbench.spring.ElementsDefinitionConstructor;
import org.minifx.workbench.spring.WorkbenchElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import com.google.common.collect.ImmutableList;

import javafx.scene.Scene;

@Configuration
@Import({ FactoryMethodsCollectorConfiguration.class, FxmlNodeServiceConfiguration.class, NodeFactoryConfiguration.class })
public class MiniFxWorkbenchConfiguration {

    private static final int DEFAULT_HEIGHT = 760;
    private static final int DEFAULT_WIDTH = 1280;

    public static final String ID_MAIN_PANEL = "minifx-workbench-main-panel";

    @Autowired(required = false)
    @Qualifier("cssStyleSheets")
    private List<List<String>> cssStyleSheets;

    @Autowired(required = false)
    private MiniFxSceneBuilder sceneBuilder;

    @Autowired(required = false)
    private List<PerspectiveProvider> perspectiveProviders;

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
    public MainPane mainPane(ApplicationEventPublisher publisher, WorkbenchElementsRepository factoryMethodsRepository,
            BeanInformationExtractor beanInformationExtractor, FxNodeFactory fxNodeFactory) {
        ElementsDefinitionConstructor viewInstantiator = new ElementsDefinitionConstructor(factoryMethodsRepository,
                beanInformationExtractor, fxNodeFactory);

        List<PerspectiveDefinition> allPerspectives = ImmutableList.<PerspectiveDefinition> builder()
                .addAll(viewInstantiator.perspectives()).addAll(providedPerspectives()).build();

        MainPane mainPanel = new MainPane(allPerspectives, factoryMethodsRepository.toolbarItems(),
                viewInstantiator.footers(), publisher, fxNodeFactory);
        mainPanel.setId(ID_MAIN_PANEL);
        return mainPanel;
    }

    @Bean
    public BeanInformationExtractor beanInformationExtractor(BeanInformationRepository factoryMethodsRepository) {
        return new BeanInormationExtractorImpl(factoryMethodsRepository);
    }

    private Iterable<PerspectiveDefinition> providedPerspectives() {
        return emptyIfNull(perspectiveProviders).stream().flatMap(p -> p.perspectives().stream()).collect(toSet());
    }

}
