/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain.definition;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.scene.Node;

/**
 * @author kfuchsbe
 */
public class DisplayProperties {

    private final String name;
    private final Node graphic;
    private final int order;

    public DisplayProperties(String name, Node graphic, int order) {
        super();
        this.name = requireNonNull(name, "name must not be null");
        this.graphic = graphic;
        this.order = order;
    }

    public String name() {
        return this.name;
    }

    public Optional<Node> graphic() {
        return Optional.ofNullable(graphic);
    }

    public int order() {
        return this.order;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((graphic == null) ? 0 : graphic.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + order;
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
        DisplayProperties other = (DisplayProperties) obj;
        if (graphic == null) {
            if (other.graphic != null) {
                return false;
            }
        } else if (!graphic.equals(other.graphic)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (order != other.order) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ComponentDisplayProperties [name=" + name + ", graphic=" + graphic + ", order=" + order + "]";
    }

}