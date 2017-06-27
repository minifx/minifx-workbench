/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons;

import static java.util.Objects.requireNonNull;
import static org.minifx.workbench.css.MiniFxCssConstants.CSS_LOCATIONS;

import java.util.Collection;
import java.util.HashSet;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Simple builder for a javaFx {@link Scene}. It includes by default the CSS style of the MiniFx framework.
 * 
 * @author acalia
 */
public final class MiniFxSceneBuilder {

    private Parent rootNode;
    private Integer width;
    private Integer height;
    private Collection<String> additionalCss;
    private boolean useMinifxStyle = false;

    private MiniFxSceneBuilder() {
        /* Factory */
    }

    public static MiniFxSceneBuilder miniFxSceneBuilder() {
        return new MiniFxSceneBuilder();
    }

    public MiniFxSceneBuilder withRoot(Parent inRootNode) {
        rootNode = inRootNode;
        return this;
    }

    public MiniFxSceneBuilder withSize(int inWidth, int inHeight) {
        width = inWidth;
        height = inHeight;
        return this;
    }

    public MiniFxSceneBuilder withMiniFxStyle() {
        useMinifxStyle = true;
        return this;
    }

    public MiniFxSceneBuilder withAdditionalCss(Collection<String> inAdditionalCss) {
        additionalCss = new HashSet<>(inAdditionalCss);
        return this;
    }

    public Scene build() {
        requireNonNull(rootNode, "root node cannot be null");
        Scene scene;
        if (width != null && height != null) {
            scene = new Scene(rootNode, width, height);
        } else {
            scene = new Scene(rootNode);
        }

        if (useMinifxStyle) {
            scene.getStylesheets().addAll(CSS_LOCATIONS);
        }

        if (additionalCss != null && !additionalCss.isEmpty()) {
            scene.getStylesheets().addAll(additionalCss);
        }

        return scene;
    }
}
