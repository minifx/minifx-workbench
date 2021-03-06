/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain.definition;

import javafx.scene.Node;

public class FooterDefinition extends TabbableDefinition<Node> {

    public FooterDefinition(Node node, DisplayProperties displayProperties, boolean alwaysShowTabs) {
        super(node, displayProperties, alwaysShowTabs);
    }

    @Override
    public String toString() {
        return "FooterDefinition [node()=" + node() + ", alwaysShowTabs()=" + alwaysShowTabs()
                + ", displayProperties()=" + displayProperties() + "]";
    }

}
