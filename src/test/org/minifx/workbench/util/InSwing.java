/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static javax.swing.SwingUtilities.isEventDispatchThread;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import javax.swing.SwingUtilities;

public class InSwing {

    private InSwing() {
        /* Only static methods */
    }

    public static final <T> T create(Supplier<T> supplier) {
        if (isEventDispatchThread()) {
            return supplier.get();
        }

        AtomicReference<T> result = new AtomicReference<>();
        try {
            SwingUtilities.invokeAndWait(() -> {
                result.set(supplier.get());
            });
        } catch (InvocationTargetException | InterruptedException e) {
            throw new RuntimeException("Error invoking supplier " + supplier + " in swing thread.");
        }
        return result.get();
    }
}
