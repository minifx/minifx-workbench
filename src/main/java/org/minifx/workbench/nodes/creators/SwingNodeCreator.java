/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes.creators;

import javax.swing.JComponent;

import org.minifx.workbench.nodes.FxNodeCreator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;

@Component
@Order(1)
public class SwingNodeCreator implements FxNodeCreator {

    @Override
    public Node fxNodeFrom(Object object) {
        if (object instanceof JComponent) {
            SwingNode swingNode = new SwingNode();
            swingNode.setContent((JComponent) object);
            return swingNode;
        } else {
            return null;
        }
    }

}
