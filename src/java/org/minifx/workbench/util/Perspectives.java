/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

public final class Perspectives {

    private Perspectives() {
        /* Only static methods */
    }

    public static final int orderFrom(Class<?> perspectiveClass) {
        requireNonNull(perspectiveClass, "perspectiveClass must not be null.");
        Order order = perspectiveClass.getAnnotation(Order.class);
        return ofNullable(order).map(Order::value).orElse(LOWEST_PRECEDENCE);
    }

}
