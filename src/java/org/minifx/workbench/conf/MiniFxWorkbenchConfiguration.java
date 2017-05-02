/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static java.util.stream.Collectors.toList;
import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;
import static org.minifx.workbench.css.MiniFxCssConstants.SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.util.MoreCollections.emptyIfNull;

import java.util.List;
import java.util.Optional;

import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.domain.MainPane;
import org.minifx.workbench.domain.ToolbarItem;
import org.minifx.workbench.domain.WorkbenchFooter;
import org.minifx.workbench.domain.WorkbenchView;
import org.minifx.workbench.spring.WorkbenchElementsRepository;
import org.minifx.workbench.util.ViewInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import javafx.scene.Node;
import javafx.scene.Scene;

@Configuration
@Import(FactoryMethodsCollectorConfiguration.class)
public class MiniFxWorkbenchConfiguration {

    private static final int DEFAULT_HEIGHT = 760;
    private static final int DEFAULT_WIDTH = 1280;

    public static final String ID_MAIN_PANEL = "minifx-workbench-main-panel";

    // @Autowired(required = false)
    // private List<WorkbenchView> views;

    @Autowired(required = false)
    @Qualifier("cssStyleSheets")
    private List<List<String>> cssStyleSheets;

    @Autowired(required = false)
    private List<ToolbarItem> toolbarItems;

    @Autowired(required = false)
    private List<WorkbenchFooter> footerItems;

    @Autowired(required = false)
    private MiniFxSceneBuilder sceneBuilder;

    @Bean
    public Scene mainScene(WorkbenchElementsRepository factoryMethodsRepository) {
        ViewInstantiator viewInstantiator = new ViewInstantiator(factoryMethodsRepository);
        MainPane mainPanel = new MainPane(viewInstantiator.perspectiveInstances(), emptyIfNull(toolbarItems),
                createFooter(viewInstantiator, emptyIfNull(footerItems)));
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

    private Optional<Node> createFooter(ViewInstantiator viewInstantiator, List<WorkbenchFooter> items) {
        if (items.isEmpty()) {
            return Optional.empty();
        }
        Node footerTabs = viewInstantiator.createContainerPaneFrom(items);
        footerTabs.getStyleClass().add(SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS);
        return Optional.of(footerTabs);
    }

}
