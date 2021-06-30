/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.minifx.fxcommons.MiniFxWorkbench;
import org.minifx.fxcommons.SingleSceneSpringJavaFxApplication;
import org.minifx.fxcommons.SingleSceneSpringJavaFxApplication.FxLauncher;
import org.minifx.workbench.conf.MiniFxWorkbenchConfiguration;
import org.minifx.workbench.conf.MiniFxWorkbenchSceneConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        return SingleSceneSpringJavaFxApplication.applicationLauncher().configurationClasses(
                ensureContainedAndLast(MiniFxWorkbenchSceneConfiguration.class, configurationClasses));
    }

    /**
     * Loads the minifx workbench from the given configuration classes, ensuring that the minifx configurations, which
     * do the magic are contained at the right place (last in the list).
     * NOTE: this must be called from the fx-thread!
     * 
     * @param configurationClasses the configuration classes to load
     * @return a {@link MiniFxWorkbench} object, containing all the configured views etc, ready to be plugged into
     *         another application.
     */
    public static MiniFxWorkbench loadFrom(Class<?>... configurationClasses) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                ensureContainedAndLast(MiniFxWorkbenchConfiguration.class, configurationClasses));
        return ctx.getBean(MiniFxWorkbench.class);
    }

    private static final Class<?>[] ensureContainedAndLast(Class<?> confToPutLast, Class<?>[] configurationClasses) {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(configurationClasses));
        classes.remove(confToPutLast);
        classes.add(confToPutLast);
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
