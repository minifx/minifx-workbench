/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.util;

import static java.util.Objects.requireNonNull;
import static javafx.fxml.FXMLLoader.CONTROLLER_SUFFIX;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FxmlControllerConvention {

    private final Class<?> controllerClass;

    private FxmlControllerConvention(Class<?> controllerClass) {
        this.controllerClass = requireNonNull(controllerClass);
    }

    public static FxmlControllerConvention of(Class<?> controllerClass) {
        return new FxmlControllerConvention(controllerClass);
    }

    private String conventionalName() {
        return controllerClass.getSimpleName().replaceAll(CONTROLLER_SUFFIX + "$", "");
    }

    public ResourceBundle resourceBundle() {
        String baseBundleName = bundleName();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseBundleName);
            return bundle;
        } catch (MissingResourceException mre) {
            return null;
        }
    }

    public String bundleName() {
        Package pckg = controllerClass.getPackage();
        if (pckg == null) {
            return conventionalName();
        }
        return pckg.getName() + "." + conventionalName();
    }

    private URL getResource(String fxmlFileName) {
        return this.controllerClass.getResource(fxmlFileName);
    }

    public URL fxmlResource() {
        String fxmlFileName = conventionalName() + ".fxml";
        URL fxmlUrl = getResource(fxmlFileName);
        if (fxmlUrl == null) {
            throw new IllegalStateException("FXML " + fxmlFileName + " not found");
        }
        return fxmlUrl;
    }

    public URL cssResource() {
        String cssName = conventionalName() + ".css";
        return getResource(cssName);
    }

}
