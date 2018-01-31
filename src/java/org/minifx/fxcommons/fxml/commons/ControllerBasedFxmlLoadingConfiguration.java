/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import static java.util.Objects.requireNonNull;
import static javafx.fxml.FXMLLoader.CONTROLLER_SUFFIX;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ControllerBasedFxmlLoadingConfiguration implements FxmlLoadingConfiguration {

    private final Class<?> controllerClass;

    private ControllerBasedFxmlLoadingConfiguration(Class<?> controllerClass) {
        this.controllerClass = requireNonNull(controllerClass);
    }

    public static ControllerBasedFxmlLoadingConfiguration of(Class<?> controllerClass) {
        return new ControllerBasedFxmlLoadingConfiguration(controllerClass);
    }

    @Override
    public String conventionalName() {
        return controllerClass.getSimpleName().replaceAll(CONTROLLER_SUFFIX + "$", "");
    }

    @Override
    public ResourceBundle resourceBundle() {
        String baseBundleName = bundleName();
        try {
            return ResourceBundle.getBundle(baseBundleName);
        } catch (MissingResourceException mre) {
            return null;
        }
    }

    String bundleName() {
        Package pckg = controllerClass.getPackage();
        if (pckg == null) {
            return conventionalName();
        }
        return pckg.getName() + "." + conventionalName();
    }

    private URL getResource(String fxmlFileName) {
        return this.controllerClass.getResource(fxmlFileName);
    }

    @Override
    public boolean hasFxmlResource() {
        return (getResource(fxmlName()) != null);
    }

    @Override
    public URL fxmlResource() {
        return getResource(fxmlName());
    }

    private String fxmlName() {
        return conventionalName() + ".fxml";
    }

    @Override
    public URL cssResource() {
        String cssName = conventionalName() + ".css";
        return getResource(cssName);
    }

    @Override
    public String toString() {
        return "ControllerBasedFxmlLoadingConfiguration [controllerClass=" + controllerClass + "]";
    }

    @Override
    public String packageName() {
        Package pckg = controllerClass.getPackage();
        if (pckg == null) {
            return null;
        }
        return pckg.getName();
    }

}
