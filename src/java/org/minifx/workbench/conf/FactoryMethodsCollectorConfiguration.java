/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import org.minifx.workbench.spring.WorkbenchElementsCollector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = WorkbenchElementsCollector.class)
public class FactoryMethodsCollectorConfiguration {
    /* only annotations relevant */
}
