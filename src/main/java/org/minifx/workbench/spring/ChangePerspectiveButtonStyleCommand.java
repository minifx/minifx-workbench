/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.util.Objects;
import java.util.function.Consumer;

import javafx.collections.ObservableList;

public final class ChangePerspectiveButtonStyleCommand extends AbstractPerspectiveEvent {

    private final Consumer<ObservableList<String>> change;

    private ChangePerspectiveButtonStyleCommand(Object perspective, Consumer<ObservableList<String>> change) {
        super(perspective);
        this.change = Objects.requireNonNull(change, "change callback must not be null");
    }

    public static ChangePerspectiveButtonStyleCommand from(Object perspective,
            Consumer<ObservableList<String>> styleChange) {
        return new ChangePerspectiveButtonStyleCommand(perspective, styleChange);
    }

    public void apply(ObservableList<String> styleClasses) {
        change.accept(styleClasses);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((change == null) ? 0 : change.hashCode());
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
        ChangePerspectiveButtonStyleCommand other = (ChangePerspectiveButtonStyleCommand) obj;
        if (change == null) {
            if (other.change != null) {
                return false;
            }
        } else if (!change.equals(other.change)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChangePerspectiveButtonStyleCommand [change=" + change + "]";
    }

}
