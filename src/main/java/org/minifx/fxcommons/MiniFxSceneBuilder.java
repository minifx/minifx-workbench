/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons;

import static java.util.Objects.requireNonNull;
import static org.minifx.workbench.css.MiniFxCssConstants.CSS_LOCATIONS;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

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
    private boolean useMinifxStyle = true;

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

    public MiniFxSceneBuilder withoutMiniFxStyle() {
        useMinifxStyle = false;
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
        applyStylesTo(scene);
        return scene;
    }

    public void applyStylesTo(Scene scene) {
        scene.getStylesheets().addAll(cssLocations());
    }

    public List<String> cssLocations() {
        Builder<String> builder = ImmutableList.builder();
        if (useMinifxStyle) {
            builder.addAll(CSS_LOCATIONS);
        }

        if (additionalCss != null && !additionalCss.isEmpty()) {
            builder.addAll(additionalCss);
        }

        return builder.build();
    }

}
