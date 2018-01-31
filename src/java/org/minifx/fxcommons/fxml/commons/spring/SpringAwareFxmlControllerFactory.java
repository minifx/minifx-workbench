/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.spring;

import org.minifx.fxmlloading.factories.impl.ControllerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class SpringAwareFxmlControllerFactory implements ControllerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAwareFxmlControllerFactory.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object call(Class<?> param) {
        LOGGER.info("Looking up controller {} in application context", param);
        return applicationContext.getBean(param);
    }

}
