/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain.definition;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;

public abstract class AbstractViewDefinition {

    private final Node node;
    private final boolean alwaysShowTabs;
    private final DisplayProperties displayProperties;

    public AbstractViewDefinition(Node node, DisplayProperties displayProperties, boolean alwaysShowTabs) {
        this.node = requireNonNull(node, "node must not be null");
        this.displayProperties = requireNonNull(displayProperties, "displayProperties must not be null");
        this.alwaysShowTabs = alwaysShowTabs;
    }

    public Node node() {
        return node;
    }

    public boolean alwaysShowTabs() {
        return alwaysShowTabs;
    }

    public DisplayProperties displayProperties() {
        return displayProperties;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (alwaysShowTabs ? 1231 : 1237);
        result = prime * result + ((displayProperties == null) ? 0 : displayProperties.hashCode());
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractViewDefinition other = (AbstractViewDefinition) obj;
        if (alwaysShowTabs != other.alwaysShowTabs) {
            return false;
        }
        if (displayProperties == null) {
            if (other.displayProperties != null) {
                return false;
            }
        } else if (!displayProperties.equals(other.displayProperties)) {
            return false;
        }
        if (node == null) {
            if (other.node != null) {
                return false;
            }
        } else if (!node.equals(other.node)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractViewDefinition [node=" + node + ", alwaysShowTabs=" + alwaysShowTabs + ", displayProperties="
                + displayProperties + "]";
    }
}
