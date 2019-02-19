/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import java.util.Optional;

public class Optionals {

    private Optionals() {
        /* only static methods */
    }

    @SafeVarargs
    public static final <T> Optional<T> first(Optional<T> o1, Optional<T> o2, Optional<T>... os) {
        if (o1.isPresent()) {
            return o1;
        }
        if (o2.isPresent()) {
            return o2;
        }
        for (Optional<T> o : os) {
            if (o.isPresent()) {
                return o;
            }
        }
        return Optional.empty();
    }

}
