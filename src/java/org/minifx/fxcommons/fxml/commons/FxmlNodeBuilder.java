/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import org.minifx.fxcommons.fxml.commons.factories.ControllerFactory;
import org.minifx.fxcommons.fxml.commons.factories.DelegatingControllerFactory;
import org.minifx.fxcommons.fxml.commons.factories.SingleControllerFactory;

import javafx.scene.Node;
import javafx.util.Callback;

public interface FxmlNodeBuilder {

    public static NonNestableFxmlNodeBuilder byConventionFrom(Object controllerInstance) {
        Objects.requireNonNull(controllerInstance);
        if (controllerInstance instanceof Class) {
            throw new IllegalArgumentException("Passed in controller must be a controller instance, not a class!");
        }
        ControllerBasedFxmlLoadingConfiguration convention = ControllerBasedFxmlLoadingConfiguration
                .of(controllerInstance.getClass());
        return new NonNestableFxmlNodeBuilder(convention, ControllerFactory.fromController(controllerInstance));
    }

    public static OngoingFxmlNodeBuilder byConventionFrom(Class<?> controllerClass) {
        Objects.requireNonNull(controllerClass);
        ControllerBasedFxmlLoadingConfiguration convention = ControllerBasedFxmlLoadingConfiguration
                .of(controllerClass);
        return new OngoingFxmlNodeBuilder(convention);
    }

    public static OngoingFxmlNodeBuilder fromFxml(String classpathResource) {
        return new OngoingFxmlNodeBuilder(ResourceBasedFxmlLoadingConfiguration.fromFxml(classpathResource));
    }

    public boolean canBuild();

    public Node build();

    public abstract static class AbstractFxmlNodeBuilder<F extends ControllerFactory> implements FxmlNodeBuilder {

        private final FxmlLoadingConfiguration configuration;
        private final F factory;

        private AbstractFxmlNodeBuilder(FxmlLoadingConfiguration configuration, F controllerFactory) {
            this.configuration = requireNonNull(configuration);
            this.factory = requireNonNull(controllerFactory, "controller factory must not be null");
        }

        @Override
        public final boolean canBuild() {
            return this.configuration.hasFxmlResource();
        }

        @Override
        public final Node build() {
            return FxmlPaneCreator.nodeFrom(configuration, factory);
        }

        final F factory() {
            return factory;
        }

        final FxmlLoadingConfiguration configuration() {
            return configuration;
        }

    }

    public static class NonNestableFxmlNodeBuilder extends AbstractFxmlNodeBuilder<SingleControllerFactory> {
        public NonNestableFxmlNodeBuilder(FxmlLoadingConfiguration configuration,
                SingleControllerFactory controllerFactory) {
            super(configuration, controllerFactory);
        }

        public NestableFxmlNodeBuilder nestedFrom(Callback<Class<?>, Object> nestedFactory) {
            return new NestableFxmlNodeBuilder(configuration(), factory().withNestedFrom(nestedFactory));
        }
    }

    public static class NestableFxmlNodeBuilder extends AbstractFxmlNodeBuilder<ControllerFactory> {

        public NestableFxmlNodeBuilder(FxmlLoadingConfiguration configuration, ControllerFactory controllerFactory) {
            super(configuration, requireNonNull(controllerFactory, "controller factory must not be null"));
        }

    }

    public static class OngoingFxmlNodeBuilder {
        private final FxmlLoadingConfiguration configuration;

        public OngoingFxmlNodeBuilder(FxmlLoadingConfiguration configuration) {
            this.configuration = configuration;
        }

        public NestableFxmlNodeBuilder controllersFrom(Callback<Class<?>, Object> controllerFactory) {
            return new NestableFxmlNodeBuilder(configuration, DelegatingControllerFactory.of(controllerFactory));
        }

    }

}
