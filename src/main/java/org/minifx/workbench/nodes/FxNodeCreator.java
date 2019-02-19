/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes;

import javafx.scene.Node;

public interface FxNodeCreator {

    public Node fxNodeFrom(Object object);

}
