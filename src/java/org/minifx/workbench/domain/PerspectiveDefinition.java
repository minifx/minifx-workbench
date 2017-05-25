/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class PerspectiveDefinition {

    private final Class<? extends Perspective> perspective;
    private final DisplayProperties displayProperties;
    private final Set<ViewDefinition> views;

    public PerspectiveDefinition(Class<? extends Perspective> perspective, DisplayProperties displayProperties,
            Set<ViewDefinition> views) {
        this.perspective = perspective;
        this.displayProperties = displayProperties;
        this.views = ImmutableSet.copyOf(views);
    }

    public Class<? extends Perspective> perspective() {
        return perspective;
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
        result = prime * result + ((perspective == null) ? 0 : perspective.hashCode());
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
        if (perspective == null) {
            if (other.perspective != null) {
                return false;
            }
        } else if (!perspective.equals(other.perspective)) {
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
        return "PerspectiveDefinition [perspective=" + perspective + ", displayProperties=" + displayProperties
                + ", views=" + views + "]";
    }

}
