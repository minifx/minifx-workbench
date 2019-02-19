/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javafx.scene.Node;

public interface FxNodeFactory {

    public Node fxNodeFrom(Object object);

    default List<Node> fxNodesFrom(List<?> objects) {
        return objects.stream().map(this::fxNodeFrom).collect(toList());
    }

}
