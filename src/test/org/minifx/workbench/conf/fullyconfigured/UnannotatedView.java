/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import org.minifx.workbench.domain.AbstractFxBorderPaneView;

import javafx.scene.control.Label;

public class UnannotatedView extends AbstractFxBorderPaneView {
    {
        setCenter(new Label("Un-annotated view."));
    }
}
