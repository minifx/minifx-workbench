/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import java.util.function.BiConsumer;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public enum PerspectivePos {

    LEFT(BorderPane::setLeft),
    RIGHT(BorderPane::setRight),
    TOP(BorderPane::setTop),
    BOTTOM(BorderPane::setBottom),
    CENTER(BorderPane::setCenter);

    private final BiConsumer<BorderPane, Node> setter;

    private PerspectivePos(BiConsumer<BorderPane, Node> setter) {
        this.setter = setter;
    }

    public OngoingSet set(Node node) {
        return new OngoingSet(this, node);
    }

    public static class OngoingSet {
        private final PerspectivePos pos;
        private final Node node;

        public OngoingSet(PerspectivePos pos, Node node) {
            super();
            this.pos = pos;
            this.node = node;
        }

        public void into(BorderPane pane) {
            pos.setter.accept(pane, node);
        }
    }
}
