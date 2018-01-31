/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.factories;

import javafx.util.Callback;

public class SingleControllerFactory extends PreferredControllerFactory {

    public SingleControllerFactory(Object controller) {
        super(controller);
    }

    @Override
    protected Object callNested(Class<?> c) {
        throw new IllegalStateException("A controller of class '" + c
                + "' is requested, while the only available controller is '" + preferredController()
                + "'. Probably you are trying to load a neste fxml (which is not supported by this method)?");
    }

    public NestedControllerFactory withNestedFrom(Callback<Class<?>, Object> nestedFactory) {
        return new NestedControllerFactory(preferredController(), nestedFactory);
    }

}