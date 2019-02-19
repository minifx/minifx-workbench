/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.spring;

import javafx.scene.Node;

public interface FxmlNodeService {

    Node nestedFromFxml(String classPathFxml);

    Node nonNestedFromController(Object controllerInstance);

    Node nestedFromController(Object controllerInstance);

}
