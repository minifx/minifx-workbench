/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.util.Objects;

public class PerspectiveButtonColorCommand extends AbstractPerspectiveEvent {

    private final String color;

    public PerspectiveButtonColorCommand(Object perspective, String color) {
        super(perspective);
        this.color = Objects.requireNonNull(color, "color must not be null");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((color == null) ? 0 : color.hashCode());
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
        PerspectiveButtonColorCommand other = (PerspectiveButtonColorCommand) obj;
        if (color == null) {
            if (other.color != null) {
                return false;
            }
        } else if (!color.equals(other.color)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PerspectiveButtonColorCommand [color=" + color + "]";
    }

}
