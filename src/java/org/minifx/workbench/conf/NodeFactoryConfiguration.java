/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import java.util.List;

import org.minifx.workbench.nodes.CreatoreBasedNodeFactory;
import org.minifx.workbench.nodes.FxNodeCreator;
import org.minifx.workbench.nodes.FxNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = FxNodeCreator.class)
public class NodeFactoryConfiguration {

    @Autowired
    private List<FxNodeCreator> creators;

    @Bean
    public FxNodeFactory nodeFactory() {
        return new CreatoreBasedNodeFactory(creators);
    }
}
