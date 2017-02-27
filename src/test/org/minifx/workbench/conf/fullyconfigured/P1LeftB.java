/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.LEFT;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.Shown;
import org.minifx.workbench.domain.AbstractFxBorderPaneView;
import org.springframework.core.annotation.Order;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

@Order(1)
@Name("named B")
@Shown(in = Perspective1.class, at = LEFT)
@Icon(icon=FontAwesomeIcon.BATTERY_QUARTER, color="green")
public class P1LeftB extends AbstractFxBorderPaneView {
    public static final String ID = "id-p1-left-b";

    public P1LeftB() {
        setId(ID);
    }

}
