/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes.creators;

import org.minifx.fxmlloading.builders.FxmlNodeBuilder;
import org.minifx.fxmlloading.builders.FxmlNodeBuilders;
import org.minifx.workbench.nodes.FxNodeCreator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

@Component
@Order(2)
public class PaneFromControllerCreator implements FxNodeCreator {

    @Override
    public Node fxNodeFrom(Object object) {
        if (object.getClass().getSimpleName().contains(FXMLLoader.CONTROLLER_SUFFIX)) {
            /* For the moment, we restrict this to non-nested controller creation */
            FxmlNodeBuilder builder = FxmlNodeBuilders.byConventionFrom(object);
            if (builder.canBuild()) {
                return builder.build();
            }
        }
        return null;
    }

}
