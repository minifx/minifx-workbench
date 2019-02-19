/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.spring;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;

public class OngoingNestedNodeCreation {

    private final FxmlNodeService nodeService;

    public OngoingNestedNodeCreation(FxmlNodeService nodeService) {
        this.nodeService = requireNonNull(nodeService, "nodeService must not be null");
    }

    public Node fromResource(String fullyQualifiedFxmlResource) {
        return nodeService.nestedFromFxml(fullyQualifiedFxmlResource);
    }

    public Node fromController(Object controllerInstance) {
        return nodeService.nestedFromController(controllerInstance);
    }

}