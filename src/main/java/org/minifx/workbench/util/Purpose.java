/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

public enum Purpose {
    PERSPECTIVE(1.5),
    VIEW(1);

    private final double sizeScaling;

    private Purpose(double sizeScaling) {
        this.sizeScaling = sizeScaling;
    }

    public double getSizeScaling() {
        return sizeScaling;
    }
}
