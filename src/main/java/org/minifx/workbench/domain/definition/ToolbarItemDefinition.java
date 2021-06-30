package org.minifx.workbench.domain.definition;

import java.util.Objects;

import javafx.scene.Node;

public class ToolbarItemDefinition {

    private final Node node;
    private final int order;

    public ToolbarItemDefinition(Node node, int order) {
        this.node = node;
        this.order = order;
    }

    public Node node() {
        return node;
    }

    public int order() {
        return order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, order);
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
        ToolbarItemDefinition other = (ToolbarItemDefinition) obj;
        return Objects.equals(node, other.node) && order == other.order;
    }

    @Override
    public String toString() {
        return "ToolbarItemDefinition [node=" + node + ", order=" + order + "]";
    }

}
