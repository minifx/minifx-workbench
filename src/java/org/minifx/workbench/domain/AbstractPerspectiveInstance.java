/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * @author kfuchsbe
 */
public class AbstractPerspectiveInstance extends BorderPane implements PerspectiveInstance {

    private final List<Object> views;
    private final String name;
    private final Node graphic;
    private final int order;

    public AbstractPerspectiveInstance(String name, Node graphic, List<Object> views, int order) {
        super();
        this.name = requireNonNull(name, "name must not be null");
        this.views = requireNonNull(views, "views must not be null");
        this.graphic = graphic;
        this.order = order;
    }

    @Override
    public String name() {
        return this.name;
    }

    public List<Object> views() {
        return views;
    }

    @Override
    public Node graphic() {
        return graphic;
    }

    @Override
    public int order() {
        return this.order;
    }

}