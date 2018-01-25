/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.minifx.fxcommons.util.FxmlControllerConvention;

import javafx.scene.Node;
import javafx.util.Callback;

public class FxmlPanels {

    /* Note: does NOT support nested calls fxml views */
    public static final Node simpleFromController(Object controller) {
        Objects.requireNonNull(controller);
        FxmlControllerConvention convention = FxmlControllerConvention.of(controller.getClass());

        Callback<Class<?>, Object> callback = new SingleUseCallback(controller);
        FxmlPaneCreator creator = new FxmlPaneCreator(convention, callback);

        return creator.create();
    }

    public static class SingleUseCallback implements Callback<Class<?>, Object> {

        private AtomicBoolean used = new AtomicBoolean(false);

        private final Object controller;

        public SingleUseCallback(Object controller) {
            this.controller = Objects.requireNonNull(controller, "controller must not be null");
        }

        @Override
        public Object call(Class<?> param) {
            if (used.getAndSet(true)) {
                throw new IllegalStateException("The Controller '" + controller
                        + "' was already injected once. A second usage is not allowed!");
            }
            return controller;
        }

    }

}
