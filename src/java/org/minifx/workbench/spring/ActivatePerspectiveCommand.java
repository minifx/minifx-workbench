/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

public class ActivatePerspectiveCommand extends AbstractPerspectiveEvent {

    public ActivatePerspectiveCommand(Object perspective) {
        super(perspective);
    }

}
