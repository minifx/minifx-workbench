/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.factories;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ClassToInstanceControllerFactory implements ControllerFactory {

    private final Map<Class<?>, Object> instances;

    public ClassToInstanceControllerFactory(Map<Class<?>, Object> instances) {
        this.instances = ImmutableMap.copyOf(requireNonNull(instances, "instances must not be null"));
    }

    public static final ClassToInstanceControllerFactory ofInstances(Object... controllerInstances) {
        requireNonNull(controllerInstances, "controllerInstances must not be null");
        Builder<Class<?>, Object> builder = ImmutableMap.builder();
        /* This will implicitely fail if the key is already contained */
        for (Object instance : controllerInstances) {
            builder.put(instance.getClass(), instance);
        }
        return new ClassToInstanceControllerFactory(builder.build());
    }

    @Override
    public Object call(Class<?> param) {
        return instances.get(param);
    }

    public ClassToInstanceControllerFactory and(Object controllerInstance) {
        requireNonNull(controllerInstance, "controllerInstance must not be null");
        Builder<Class<?>, Object> builder = ImmutableMap.builder();
        builder.putAll(instances);
        builder.put(controllerInstance.getClass(), controllerInstance);
        /* Will throw implicitely, if the key already exists */
        return new ClassToInstanceControllerFactory(builder.build());
    }

}
