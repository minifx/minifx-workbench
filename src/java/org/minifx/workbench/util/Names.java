/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Utility methods to determine names for objects.
 * 
 * @author kfuchsbe
 */
public final class Names {

    private Names() {
        /* only static methods */
    }

    /**
     * Tries to get the name of an object from one of its methods. Only methods which return strings and have no
     * parameters are considered. the search is done in the following order:
     * <ol>
     * <li>"name()"
     * <li>"getName()"
     * </ol>
     * 
     * @param object the object for which to retrieve the name
     * @return the name as derived from a name method, if available.
     */
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
