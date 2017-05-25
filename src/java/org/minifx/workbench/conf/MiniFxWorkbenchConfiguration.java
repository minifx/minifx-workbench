/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static java.util.stream.Collectors.toList;
import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;

import java.util.List;

import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.domain.MainPane;
import org.minifx.workbench.spring.WorkbenchElementsRepository;
import org.minifx.workbench.util.ViewInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javafx.scene.Scene;

@Configuration
@Import(FactoryMethodsCollectorConfiguration.class)
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
    public Scene mainScene(WorkbenchElementsRepository factoryMethodsRepository) {
        ViewInstantiator viewInstantiator = new ViewInstantiator(factoryMethodsRepository);

        MainPane mainPanel = new MainPane(viewInstantiator.perspectives(), factoryMethodsRepository.toolbarItems(),
                viewInstantiator.footers());

        mainPanel.setId(ID_MAIN_PANEL);

        if (sceneBuilder == null) {
            sceneBuilder = miniFxSceneBuilder().withSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }

        sceneBuilder.withRoot(mainPanel);

        if (cssStyleSheets != null) {
            sceneBuilder.withAdditionalCss(cssStyleSheets.stream().flatMap(List::stream).collect(toList()));
        }

        return sceneBuilder.build();
    }

}
