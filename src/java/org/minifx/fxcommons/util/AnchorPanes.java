/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public final class AnchorPanes {

    private AnchorPanes() {
        /* static methods */
    }
    
    /**
     * Wrap the given {@link Node} with an {@link AnchorPane} and apply the specified anchor value
     */
    public static AnchorPane wrapWithAnchor(Node node, double anchorValue) {
        AnchorPane box = new AnchorPane(node);
        anchorFor(node, anchorValue);
        return box;
    }
    
    /**
     * Wrap the given {@link Node} with an {@link AnchorPane} and apply 0.0 as anchor value
     */
    public static AnchorPane wrapWithAnchor(Node node) {
        AnchorPane box = new AnchorPane(node);
        zeroAnchorFor(node);
        return box;
    }

    /**
     * Same as {@link #anchorFor(Node, double)} but with 0.0 as anchor value
     */
    public static void zeroAnchorFor(Node target) {
        anchorFor(target, 0.0);
    }

    /**
     * Apply the {@link AnchorPane#setTopAnchor(Node, Double)} (and others) to the given target
     */
    public static void anchorFor(Node target, double value) {
        AnchorPane.setTopAnchor(target, value);
        AnchorPane.setRightAnchor(target, value);
        AnchorPane.setBottomAnchor(target, value);
        AnchorPane.setLeftAnchor(target, value);
    }

}
