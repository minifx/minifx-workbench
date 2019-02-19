/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain.definition;

import javafx.scene.Node;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author kfuchsbe
 */
public class DisplayProperties {

    private final String name;
    private final Node graphic;
    private final int order;
    private final boolean hasGutters;

    public DisplayProperties(String name, Node graphic, int order) {
        this(name, graphic, order, false);
    }

    public DisplayProperties(String name, Node graphic, int order, boolean hasGutters) {
        this.name = requireNonNull(name, "name must not be null");
        this.graphic = graphic;
        this.order = order;
        this.hasGutters = hasGutters;
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

    public boolean hasGutters() {
        return hasGutters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DisplayProperties that = (DisplayProperties) o;
        return order == that.order && hasGutters == that.hasGutters && Objects.equals(name, that.name) && Objects
                .equals(graphic, that.graphic);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, graphic, order, hasGutters);
    }

    @Override
    public String toString() {
        return "DisplayProperties{" + "name='" + name + '\'' + ", graphic=" + graphic + ", order=" + order + ", " +
                "hasGutters=" + hasGutters + '}';
    }
}