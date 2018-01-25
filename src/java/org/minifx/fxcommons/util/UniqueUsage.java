/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class UniqueUsage {

    private final Set<Object> alreadyUsed = Collections.newSetFromMap(new WeakHashMap<Object, Boolean>());
    private final Object monitor = new Object();

    public <T> T uniqueOrThrow(T object) {
        synchronized (monitor) {
            if (alreadyUsed.contains(object)) {
                throw new IllegalStateException(
                        "The object '" + object + "' is going to be used twice. This is not allowed!");
            }
            alreadyUsed.add(object);
            return object;
        }
    }

}
