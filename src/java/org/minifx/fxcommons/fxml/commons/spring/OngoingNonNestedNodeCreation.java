/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.spring;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;

public class OngoingNonNestedNodeCreation {

    private final FxmlNodeService nodeService;

    public OngoingNonNestedNodeCreation(FxmlNodeService nodeService) {
        super();
        this.nodeService = requireNonNull(nodeService, "nodeService must not be null");
    }

    public Node fromController(Object controllerInstance) {
        return nodeService.nonNestedFromController(controllerInstance);
    }

}