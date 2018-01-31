/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.factories;

import static java.util.Objects.requireNonNull;

import javafx.util.Callback;

public class DelegatingControllerFactory implements ControllerFactory {

    private Callback<Class<?>, Object> delegate;

    private DelegatingControllerFactory(Callback<Class<?>, Object> delegate) {
        this.delegate = requireNonNull(delegate, "delegate must not be null");
    }

    public static DelegatingControllerFactory of(Callback<Class<?>, Object> delegate) {
        return new DelegatingControllerFactory(delegate);
    }

    @Override
    public Object call(Class<?> param) {
        return delegate.call(param);
    }

}
