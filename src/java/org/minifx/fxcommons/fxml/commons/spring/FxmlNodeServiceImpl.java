/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.spring;

import static java.util.Objects.requireNonNull;

import org.minifx.fxmlloading.builder.FxmlNodeBuilder;
import org.minifx.fxmlloading.factories.impl.ControllerFactory;

import javafx.scene.Node;

public class FxmlNodeServiceImpl implements FxmlNodeService {

    private final ControllerFactory controllerFactory;

    public FxmlNodeServiceImpl(ControllerFactory controllerFactory) {
        this.controllerFactory = requireNonNull(controllerFactory, "controllerFactory must not be null");
    }

    @Override
    public Node nestedFromFxml(String classPathFxml) {
        return FxmlNodeBuilder.fromFxml(classPathFxml).controllersFrom(controllerFactory).build();
    }

    @Override
    public Node nonNestedFromController(Object controllerInstance) {
        return FxmlNodeBuilder.byConventionFrom(controllerInstance).build();
    }

    @Override
    public Node nestedFromController(Object controllerInstance) {
        return FxmlNodeBuilder.byConventionFrom(controllerInstance).nestedFrom(controllerFactory).build();
    }

}
