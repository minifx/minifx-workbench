/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import org.minifx.workbench.annotations.View;

import javafx.scene.control.Label;

@View(in = Perspective1.class, at = CENTER)
public class P1CenterA extends AbstractFxBorderPaneView {

    public static final String ID = "id-p1-center-a";

    public P1CenterA() {
        setId(ID);
        setPrefSize(500, 500);
        setCenter(new Label(getClass().getSimpleName()));
    }

}