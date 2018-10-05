/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import org.minifx.workbench.conf.MiniFxWorkbenchConfiguration;

import static org.minifx.fxcommons.SingleSceneSpringJavaFxApplication.applicationLauncher;

public class DemoMain {

    public static void main(String[] args) {
        applicationLauncher()
                .configurationClasses(MiniFxWorkbenchConfiguration.class, DemoSimpleExampleConfiguration.class)
                .launch(args);
    }

}
