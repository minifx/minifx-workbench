/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.util.Objects;

public abstract class AbstractPerspectiveEvent {

    private final Object perspective;

    public AbstractPerspectiveEvent(Object perspective) {
        this.perspective = Objects.requireNonNull(perspective);
    }

    public Object perspective() {
        return perspective;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((perspective == null) ? 0 : perspective.hashCode());
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
        AbstractPerspectiveEvent other = (AbstractPerspectiveEvent) obj;
        if (perspective == null) {
            if (other.perspective != null) {
                return false;
            }
        } else if (!perspective.equals(other.perspective)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [perspective=" + perspective + "]";
    }

}
