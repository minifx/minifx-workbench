/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import org.minifx.fxcommons.fxml.commons.factories.ControllerFactory;
import org.minifx.fxcommons.fxml.commons.spring.FxmlNodeService;
import org.minifx.fxcommons.fxml.commons.spring.FxmlNodeServiceImpl;
import org.minifx.fxcommons.fxml.commons.spring.SpringAwareFxmlControllerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FxmlNodeServiceConfiguration {

    @Bean
    public ControllerFactory controllerFactory() {
        return new SpringAwareFxmlControllerFactory();
    }

    @Bean
    public FxmlNodeService fxmlNodeService(ControllerFactory controllerFactory) {
        return new FxmlNodeServiceImpl(controllerFactory);
    }
}
