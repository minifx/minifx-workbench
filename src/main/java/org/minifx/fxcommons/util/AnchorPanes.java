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
     *
     * @param node        the node to wrap
     * @param anchorValue the anchor value to apply to the node
     * @return an anchor pane with the node wrapped
     */
    public static AnchorPane wrapWithAnchor(Node node, double anchorValue) {
        AnchorPane box = new AnchorPane(node);
        anchorFor(node, anchorValue);
        return box;
    }

    /**
     * Wrap the given {@link Node} with an {@link AnchorPane} and apply 0.0 as anchor value
     *
     * @param node the node to wrap
     * @return an anchor pane with the node wrapped
     */
    public static AnchorPane wrapWithAnchor(Node node) {
        AnchorPane box = new AnchorPane(node);
        zeroAnchorFor(node);
        return box;
    }

    /**
     * Same as {@link #anchorFor(Node, double)} but with 0.0 as anchor value
     *
     * @param target the node to wrap
     */
    public static void zeroAnchorFor(Node target) {
        anchorFor(target, 0.0);
    }

    /**
     * Apply the {@link AnchorPane#setTopAnchor(Node, Double)} (and others) to the given target
     *
     * @param target the node to wrap
     * @param value  the anchor value to use
     */
    public static void anchorFor(Node target, double value) {
        AnchorPane.setTopAnchor(target, value);
        AnchorPane.setRightAnchor(target, value);
        AnchorPane.setBottomAnchor(target, value);
        AnchorPane.setLeftAnchor(target, value);
    }

}
