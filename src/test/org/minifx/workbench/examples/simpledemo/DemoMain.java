/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import static org.minifx.fxcommons.SingleSceneSpringJavaFxApplication.applicationLauncher;

import org.apache.log4j.BasicConfigurator;
import org.minifx.workbench.conf.MiniFxWorkbenchConfiguration;

public class DemoMain {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        applicationLauncher()
                .configurationClasses(MiniFxWorkbenchConfiguration.class, DemoSimpleExampleConfiguration.class)
                .launch(args);
    }

}
