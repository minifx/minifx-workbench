/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import org.minifx.workbench.domain.Perspective;

public class ActivatePerspectiveCommand extends AbstractPerspectiveEvent {

    public ActivatePerspectiveCommand(Class<? extends Perspective> perspective) {
        super(perspective);
    }

}
