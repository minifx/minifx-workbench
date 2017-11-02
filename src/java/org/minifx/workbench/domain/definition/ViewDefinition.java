/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain.definition;

import static java.util.Objects.requireNonNull;

import org.minifx.workbench.domain.PerspectivePos;

import javafx.scene.Node;

public class ViewDefinition extends TabbableDefinition<Node> {

    private final PerspectivePos perspectivePos;

    public ViewDefinition(Node node, PerspectivePos perspectivePos, DisplayProperties displayProperties,
            boolean alwaysShowTabs) {
        super(node, displayProperties, alwaysShowTabs);
        this.perspectivePos = requireNonNull(perspectivePos, "perspectivePos must not be null");
    }

    public PerspectivePos perspectivePos() {
        return perspectivePos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((perspectivePos == null) ? 0 : perspectivePos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ViewDefinition other = (ViewDefinition) obj;
        if (perspectivePos != other.perspectivePos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViewDefinition [perspectivePos=" + perspectivePos + ", node()=" + node() + ", alwaysShowTabs()="
                + alwaysShowTabs() + ", displayProperties()=" + displayProperties() + "]";
    }

}
