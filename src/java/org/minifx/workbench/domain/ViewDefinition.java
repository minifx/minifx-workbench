/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.View;

import javafx.scene.Node;

public class ViewDefinition {

    private final String name;
    private final View viewAnnotation;
    private final Icon iconAnnotation;
    private final Node node;

    public ViewDefinition(String name, View viewAnnotation, Icon iconAnnotation, Node node) {
        this.name = name;
        this.viewAnnotation = viewAnnotation;
        this.iconAnnotation = iconAnnotation;
        this.node = node;
    }

    public String name() {
        return this.name;
    }

    public View viewAnnotation() {
        return this.viewAnnotation;
    }

    public Icon iconAnnotation() {
        return this.iconAnnotation;
    }

    public Node node() {
        return this.node;
    }

}
