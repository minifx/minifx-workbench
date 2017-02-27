/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import org.minifx.workbench.annotations.Shown;
import org.minifx.workbench.domain.AbstractFxBorderPaneView;
import org.springframework.stereotype.Component;

@Component
@Shown(in = Perspective1.class, at = CENTER)
public class P1CenterBNotInConfig extends AbstractFxBorderPaneView {

    public P1CenterBNotInConfig() {
        setPrefSize(300, 300);
    }
}
