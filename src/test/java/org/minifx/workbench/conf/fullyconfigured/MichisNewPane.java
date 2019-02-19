/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.RIGHT;

import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.stereotype.Component;

@Component
@Name("michi")
@View(in = Perspective2.class, at = RIGHT, enforceTab=true)
public class MichisNewPane extends AbstractFxBorderPaneView {
    /* marker */
}
