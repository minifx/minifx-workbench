/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static java.util.stream.Collectors.toList;
import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;
import static org.minifx.workbench.util.MoreCollections.emptyIfNull;
import static org.minifx.workbench.util.Views.perspectiveInstancesFrom;

import java.util.List;

import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.domain.MainPane;
import org.minifx.workbench.domain.ToolbarItem;
import org.minifx.workbench.domain.WorkbenchView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javafx.scene.Scene;

@Configuration
public class MiniFxWorkbenchConfiguration {

    public static final String ID_MAIN_PANEL = "minifx-workbench-main-panel";

    @Autowired(required = false)
    private List<WorkbenchView> views;

    @Autowired(required = false)
    @Qualifier("cssStyleSheets")
    private List<List<String>> cssStyleSheets;

    @Autowired(required = false)
    private List<ToolbarItem> toolbarItems;

    @Bean
    public Scene mainScene() {
        MainPane mainPanel = new MainPane(perspectiveInstancesFrom(emptyIfNull(views)), emptyIfNull(toolbarItems));
        mainPanel.setId(ID_MAIN_PANEL);

        MiniFxSceneBuilder sceneBuilder = miniFxSceneBuilder().withRoot(mainPanel).withSize(1280, 760);

        if (cssStyleSheets != null) {
            sceneBuilder.withAdditionalCss(cssStyleSheets.stream().flatMap(List::stream).collect(toList()));
        }

        return sceneBuilder.build();
    }

}
