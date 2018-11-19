/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.util;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public final class Fillers {

    private Fillers() {
        /* static methods */
    }

    /**
     * Creates a vertical filler
     *
     * @return a new instance of a vertical filler
     */
    public static Node verticalFiller() {
        VBox filler = new VBox();
        VBox.setVgrow(filler, Priority.ALWAYS);
        return filler;
    }

    /**
     * Creates an horizontal filler
     *
     * @return a new instance of a horizontal filler
     */
    public static Node horizontalFiller() {
        HBox filler = new HBox();
        HBox.setHgrow(filler, Priority.ALWAYS);
        return filler;
    }

}
