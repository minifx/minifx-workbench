/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain.definition;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class PerspectiveDefinition {

    private final Object perspectiveKey;
    private final DisplayProperties displayProperties;
    private final Set<ViewDefinition> views;

    public PerspectiveDefinition(Object perspectiveKey, DisplayProperties displayProperties,
            Set<ViewDefinition> views) {
        this.perspectiveKey = requireNonNull(perspectiveKey, "perspectiveKey must not be null");
        this.displayProperties = requireNonNull(displayProperties, "displayProperties must not be null");
        this.views = ImmutableSet.copyOf(requireNonNull(views, "views must not be null"));
    }

    public Object perspective() {
        return perspectiveKey;
    }

    public DisplayProperties displayProperties() {
        return displayProperties;
    }

    public final Set<ViewDefinition> views() {
        return views;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((displayProperties == null) ? 0 : displayProperties.hashCode());
        result = prime * result + ((perspectiveKey == null) ? 0 : perspectiveKey.hashCode());
        result = prime * result + ((views == null) ? 0 : views.hashCode());
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
        PerspectiveDefinition other = (PerspectiveDefinition) obj;
        if (displayProperties == null) {
            if (other.displayProperties != null) {
                return false;
            }
        } else if (!displayProperties.equals(other.displayProperties)) {
            return false;
        }
        if (perspectiveKey == null) {
            if (other.perspectiveKey != null) {
                return false;
            }
        } else if (!perspectiveKey.equals(other.perspectiveKey)) {
            return false;
        }
        if (views == null) {
            if (other.views != null) {
                return false;
            }
        } else if (!views.equals(other.views)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PerspectiveDefinition [perspective=" + perspectiveKey + ", displayProperties=" + displayProperties
                + ", views=" + views + "]";
    }

}
