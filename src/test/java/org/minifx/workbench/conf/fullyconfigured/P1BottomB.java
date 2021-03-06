/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.BOTTOM;

import org.minifx.workbench.annotations.View;
import org.springframework.stereotype.Component;

@Component
@View(in = Perspective1.class, at = BOTTOM)
public class P1BottomB extends AbstractFxBorderPaneView {

    public P1BottomB() {
        setPrefSize(200, 50);
    }

}
