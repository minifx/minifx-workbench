/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class Names {

    public static final Optional<String> nameFromNameMethod(Object object) {
        Optional<Method> nameMethod = nameMethod(object);
        if (nameMethod.isPresent()) {
            try {
                return Optional.ofNullable((String) nameMethod.get().invoke(object));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                /* do nothing on purpose */
            }
        }
        return Optional.empty();
    }

    private static Optional<Method> nameMethod(Object object) {
        return Optionals.first(stringMethodOfName(object, "name"), stringMethodOfName(object, "getName"));
    }

    private static Optional<Method> stringMethodOfName(Object object, String methodName) {
        try {
            Method nameMethod = object.getClass().getMethod(methodName);
            if (returnsString(nameMethod)) {
                return Optional.of(nameMethod);
            }
        } catch (NoSuchMethodException e) {
            /* do nothing on purpose */
        }
        return Optional.empty();
    }

    private static boolean returnsString(Method nameMethod) {
        return String.class.isAssignableFrom(nameMethod.getReturnType());
    }

}
