/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class UniqueUsage {

    private final Set<Object> alreadyUsed = Collections.newSetFromMap(new WeakHashMap<Object, Boolean>());
    private final Object monitor = new Object();

    public boolean isUsed(Object object) {
        synchronized (monitor) {
            return alreadyUsed.contains(object);
        }
    }

    public <T> T useOnceOrThrow(T object, String message) {
        synchronized (monitor) {
            if (isUsed(object)) {
                throw new IllegalStateException(message);
            }
            alreadyUsed.add(object);
            return object;
        }
    }

    public <T> T useOnceOrThrow(T object) {
        return useOnceOrThrow(object, "The object '" + object + "' is going to be used twice. This is not allowed!");
    }

}
