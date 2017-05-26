/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import org.minifx.workbench.domain.Perspective;

public class PerspectiveActivatedEvent extends AbstractPerspectiveEvent {

    public PerspectiveActivatedEvent(Class<? extends Perspective> perspective) {
        super(perspective);
    }

}
