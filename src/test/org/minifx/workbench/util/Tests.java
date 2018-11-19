/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import java.util.Set;

import org.testfx.service.query.NodeQuery;

import javafx.scene.Node;

public class Tests {

    public static final String idRef(String id) {
        return "#" + id;
    }

    public static final Node single(NodeQuery lookup) {
        Set<Node> nodes = lookup.queryAll();
        if (nodes.size() != 1) {
            throw new IllegalArgumentException(
                    "Exactly one item expected. However the following nodes were found: " + nodes);
        }
        return nodes.iterator().next();
    }

}
