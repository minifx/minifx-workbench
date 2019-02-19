/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import java.util.Collections;
import java.util.List;

public class MoreCollections {

    private MoreCollections() {
        /* only static methods */
    }

    public static final <T> List<T> emptyIfNull(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

}
