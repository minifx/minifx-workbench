/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.scene.Node;

public class CreatoreBasedNodeFactory implements FxNodeFactory {

    private final List<FxNodeCreator> creators;

    public CreatoreBasedNodeFactory(List<FxNodeCreator> creators) {
        this.creators = requireNonNull(creators, "creators must not be null");
    }

    @Override
    public Node fxNodeFrom(Object object) {
        requireNonNull("Object to create node from must not be null!");
        for (FxNodeCreator creator : creators) {
            Node node = creator.fxNodeFrom(object);
            if (node != null) {
                return node;
            }
        }
        throw new IllegalArgumentException(
                "Any MiniFx component must be convertible into a javafx node. This is not the case for the object '"
                        + object + "'. Available nodeCreators: " + creators);
    }

}
