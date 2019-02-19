/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.TOP;

import org.minifx.workbench.annotations.View;
import org.springframework.stereotype.Component;

@Component
@View(in = Perspective1.class, at = TOP)
public class P1TopA extends AbstractFxBorderPaneView {
    public P1TopA() {
        setPrefSize(200, 200);
    }

}
