/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author kfuchsbe
 */
public abstract class AbstractSpringApplicationTest extends ApplicationTest {

    private final Class<?>[] configurationClasses;
    private ApplicationContext ctx;

    public AbstractSpringApplicationTest(Class<?>... configurationClasses) {
        this.configurationClasses = Arrays.copyOf(configurationClasses, configurationClasses.length);
    }

    @Override
    public void start(Stage stage) {
        ctx = new AnnotationConfigApplicationContext(configurationClasses);
        stage.setScene(ctx.getBean(Scene.class));
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }

}