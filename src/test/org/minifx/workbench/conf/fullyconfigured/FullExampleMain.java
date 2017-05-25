/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import org.apache.log4j.BasicConfigurator;
import org.minifx.workbench.MiniFx;

public class FullExampleMain {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        MiniFx.launcher(FullExampleConfiguration.class).launch(args);
    }

}
