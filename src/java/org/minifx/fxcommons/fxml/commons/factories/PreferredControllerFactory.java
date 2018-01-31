/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.factories;

import static java.util.Objects.requireNonNull;

import org.minifx.fxcommons.fxml.commons.FxmlControllers;

public abstract class PreferredControllerFactory implements ControllerFactory {

    private final Object preferredController;

    public PreferredControllerFactory(Object controller) {
        this.preferredController = requireNonNull(controller, "Controller must not be null");
    }

    @Override
    public final Object call(Class<?> c) {
        if (c.isInstance(preferredController())) {
            return FxmlControllers.useOnceOrThrow(preferredController());
        }
        return callNested(c);
    }

    protected abstract Object callNested(Class<?> c);

    protected Object preferredController() {
        return preferredController;
    }

}