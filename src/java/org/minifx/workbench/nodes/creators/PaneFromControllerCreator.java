/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes.creators;

import org.minifx.fxcommons.fxml.commons.FxmlNodeBuilder;
import org.minifx.fxcommons.fxml.commons.factories.ControllerFactory;
import org.minifx.workbench.nodes.FxNodeCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

@Component
@Order(2)
public class PaneFromControllerCreator implements FxNodeCreator {

    @Autowired
    private ControllerFactory controllerFactory;

    @Override
    public Node fxNodeFrom(Object object) {
        if (object.getClass().getSimpleName().contains(FXMLLoader.CONTROLLER_SUFFIX)) {
            /* For the moment, we restrict this to non-nested controller creation */
            FxmlNodeBuilder builder = FxmlNodeBuilder.byConventionFrom(object);
            if (builder.canBuild()) {
                return builder.build();
            }
        }
        return null;
    }

}
