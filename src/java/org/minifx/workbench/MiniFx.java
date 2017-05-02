/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.minifx.fxcommons.SingleSceneSpringJavaFxApplication;
import org.minifx.fxcommons.SingleSceneSpringJavaFxApplication.FxLauncher;
import org.minifx.workbench.conf.MiniFxWorkbenchConfiguration;

public class MiniFx {

    private MiniFx() {
        /* only static methods */
    }

    /**
     * This way to get a launcher guarantees that the configuration class for miniFx is present and at the right place
     * (last in the list)
     * 
     * @param configurationClasses the configuration classes to launch
     * @return the launcher
     */
    public static FxLauncher launcher(Class<?>... configurationClasses) {
        return SingleSceneSpringJavaFxApplication.applicationLauncher()
                .configurationClasses(ensureMiniFxConfigIsContainedAndLast(configurationClasses));
    }

    private static final Class<?>[] ensureMiniFxConfigIsContainedAndLast(Class<?>... configurationClasses) {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(configurationClasses));
        classes.remove(MiniFxWorkbenchConfiguration.class);
        classes.add(MiniFxWorkbenchConfiguration.class);
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
