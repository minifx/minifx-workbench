/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import org.minifx.workbench.spring.WorkbenchElementsPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryMethodsCollectorConfiguration {

    @Bean
    public WorkbenchElementsPostProcessor workbenchElementsPostProcessor() {
        return new WorkbenchElementsPostProcessor();
    }
    
}
