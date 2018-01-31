/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes.creators;

import org.minifx.workbench.nodes.FxNodeCreator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javafx.scene.Node;

@Component
@Order(0)
public class AlreadyNodeCreator implements FxNodeCreator {

    @Override
    public Node fxNodeFrom(Object object) {
        if (object instanceof Node) {
            return (Node) object;
        } else {
            return null;
        }
    }

}
