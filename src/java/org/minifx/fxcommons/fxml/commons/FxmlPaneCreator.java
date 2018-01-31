/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class FxmlPaneCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FxmlPaneCreator.class);

    private final Callback<Class<?>, Object> controllerFactory;
    private final FxmlLoadingConfiguration configuration;

    private FxmlPaneCreator(FxmlLoadingConfiguration convention, Callback<Class<?>, Object> childControllerFactory) {
        this.configuration = requireNonNull(convention, "controller convention must not be null");
        this.controllerFactory = requireNonNull(childControllerFactory, "controllerFactory must not be null");
    }

    public static final Node nodeFrom(FxmlLoadingConfiguration convention,
            Callback<Class<?>, Object> childControllerFactory) {
        return new FxmlPaneCreator(convention, childControllerFactory).create();
    }

    private Node create() {
        Parent root = loadFxml();
        addCssIfPresent(root);
        return stackPaneOf(root);
    }

    private Parent loadFxml() {
        URL fxmlUrl = configuration.fxmlResource();
        try {
            FXMLLoader loader = new FXMLLoader(fxmlUrl, configuration.resourceBundle());
            loader.setLocation(fxmlUrl);
            loader.setControllerFactory(this::provideController);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load fx node from '" + fxmlUrl + "'", e);
        }
    }

    private static final Node stackPaneOf(Parent root) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        return stackPane;
    }

    private Object provideController(Class<?> clazz) {
        Object controller = controllerFactory.call(clazz);

        if (controller == null || !clazz.isAssignableFrom(controller.getClass())) {
            throw new IllegalStateException("ControllerFactory [" + controllerFactory.getClass().getName()
                    + "] returned a controller [" + controller + "] that is not compatible with the requested type ["
                    + clazz.getName() + "]");
        }
        return controller;
    }

    /**
     * @deprecated makes no sense as it does not work with nested views
     */
    @Deprecated
    private void addCssIfPresent(Parent root) {
        URL cssUrl = configuration.cssResource();
        if (cssUrl != null) {
            LOGGER.info("Applying CSS: {}", cssUrl);
            root.getStylesheets().add(cssUrl.toExternalForm());
        }
    }

}
