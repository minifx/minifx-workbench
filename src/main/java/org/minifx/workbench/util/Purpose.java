/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

public enum Purpose {
    PERSPECTIVE("1.5em"),
    VIEW("1em");

    private Purpose(String defaultIconSize) {
        this.defaultIconSize = defaultIconSize;
    }

    private final String defaultIconSize;

    public String defaultIconSize() {
        return this.defaultIconSize;
    }

}
