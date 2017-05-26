/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.RIGHT;

import org.minifx.workbench.annotations.View;
import org.springframework.stereotype.Component;

import javafx.scene.control.Label;

@View(in = Perspective1.class, at = RIGHT)
@Component
public class P1RightA extends AbstractFxBorderPaneView {
    
    {
        setCenter(new Label("P1RightA"));
    }
    /* marker */
}
