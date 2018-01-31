/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.factories;

import javafx.util.Callback;

public interface ControllerFactory extends Callback<Class<?>, Object> {

    public static SingleControllerFactory fromController(Object controller) {
        return new SingleControllerFactory(controller);
    }

}
