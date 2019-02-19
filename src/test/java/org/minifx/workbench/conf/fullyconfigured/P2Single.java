/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import org.minifx.workbench.annotations.View;
import org.springframework.stereotype.Component;

import javafx.scene.control.Label;

@Component
@View(in = Perspective2.class, at = CENTER)
public class P2Single extends AbstractFxBorderPaneView {
    
    {
        setCenter(new Label("Empty View"));
    }
    /* marker */
}
