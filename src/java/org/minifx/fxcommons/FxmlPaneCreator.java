/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;

import org.minifx.fxcommons.util.FxmlControllerConvention;
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
    private final FxmlControllerConvention convention;

    public FxmlPaneCreator(FxmlControllerConvention convention, Callback<Class<?>, Object> childControllerFactory) {
        this.convention = requireNonNull(convention, "controller convention must not be null");
        this.controllerFactory = requireNonNull(childControllerFactory, "controllerFactory must not be null");
    }

    public Node create() {
        Parent root = loadFxml();
        addCssIfPresent(root);
        return stackPaneOf(root);
    }

    private Parent loadFxml() {
        URL fxmlUrl = convention.fxmlResource();
        try {
            FXMLLoader loader = new FXMLLoader(fxmlUrl, convention.resourceBundle());
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

    private void addCssIfPresent(Parent root) {
        URL cssUrl = convention.cssResource();
        if (cssUrl != null) {
            LOGGER.info("Applying CSS: " + cssUrl);
            root.getStylesheets().add(cssUrl.toExternalForm());
        }
    }

}
