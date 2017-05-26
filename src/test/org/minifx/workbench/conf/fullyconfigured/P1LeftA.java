/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.LEFT;

import org.minifx.workbench.annotations.View;
import org.springframework.core.annotation.Order;

@Order(1)
@View(in = Perspective1.class, at = LEFT)
class P1LeftA extends AbstractFxBorderPaneView {

    private static final String ID = "id-p1-left-a";

    public P1LeftA() {
        setId(ID);
    }

}