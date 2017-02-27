/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.swing.JComponent;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;

public class MiniFxComponents {

    private MiniFxComponents() {
        /* only static methods */
    }

    public static Node fxNodeFrom(Object view) {
        if (view instanceof Node) {
            return (Node) view;
        }
        if (view instanceof JComponent) {
            SwingNode swingNode = new SwingNode();
            swingNode.setContent((JComponent) view);
            return swingNode;
        }
        throw new IllegalArgumentException(
                "A view must either be a java-fx Node or swing JComponent. None of this is the case for the view "
                        + view + ".");
    }

    public static final List<Node> nodesFrom(List<?> toolbarItems) {
        return toolbarItems.stream().map(MiniFxComponents::fxNodeFrom).collect(toList());
    }
    
    
}
