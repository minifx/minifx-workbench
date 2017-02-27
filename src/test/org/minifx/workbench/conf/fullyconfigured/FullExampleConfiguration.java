/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import org.minifx.workbench.util.InSwing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class FullExampleConfiguration {

    @Bean
    public P1LeftA p1LeftViewA() {
        return new P1LeftA();
    }

    @Bean
    public P1CenterA p1CenterViewA() {
        return new P1CenterA();
    }

    @Bean
    public P1LeftB p1LeftViewB() {
        return new P1LeftB();
    }

    @Bean
    public UnannotatedView unannotatedView() {
        return new UnannotatedView();
    }

    @Bean
    public EmbeddedSwingPanel swingNode() {
        return InSwing.create(EmbeddedSwingPanel::new);
    }

}