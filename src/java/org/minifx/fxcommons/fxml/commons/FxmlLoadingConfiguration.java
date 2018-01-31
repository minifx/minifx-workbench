/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public interface FxmlLoadingConfiguration {

    default boolean hasFxmlResource() {
        return (fxmlResource() != null);
    }

    default ResourceBundle resourceBundle() {
        String baseBundleName = conventionalName();
        if (packageName() != null) {
            baseBundleName = packageName() + "." + conventionalName();
        }
        try {
            return ResourceBundle.getBundle(baseBundleName);
        } catch (MissingResourceException mre) {
            return null;
        }
    }

    URL fxmlResource();

    URL cssResource();

    String conventionalName();

    String packageName();
}