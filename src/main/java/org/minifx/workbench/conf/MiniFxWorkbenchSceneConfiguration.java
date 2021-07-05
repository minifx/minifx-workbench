/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import org.minifx.fxcommons.MiniFxWorkbench;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javafx.scene.Scene;

@Configuration
@Import({ MiniFxWorkbenchConfiguration.class })
public class MiniFxWorkbenchSceneConfiguration {

    @Bean
    public Scene mainScene(MiniFxWorkbench workbench) {
        return workbench.createScene();
    }

}
