/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import javafx.scene.Node;

public interface PerspectiveInstance {

    String name();

    Node graphic();

    int order();

}
