/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import static java.util.Objects.requireNonNull;

import java.net.URL;

import com.google.common.annotations.VisibleForTesting;

public class ResourceBasedFxmlLoadingConfiguration implements FxmlLoadingConfiguration {

    private static final String CSS_SUFFIX = ".css";
    private static final String FXML_SUFFIX = ".fxml";

    private final String fullFxmlResourceName;

    private ResourceBasedFxmlLoadingConfiguration(String fullyQualifiedFxmlName) {
        this.fullFxmlResourceName = requireNonNull(fullyQualifiedFxmlName);
        if (this.fullFxmlResourceName.isEmpty()) {
            throw new IllegalArgumentException("resource name must not be empty.");
        }
        if (!this.fullFxmlResourceName.endsWith(FXML_SUFFIX)) {
            throw new IllegalArgumentException("resource name must end in '" + FXML_SUFFIX
                    + "'. This is not the case for given name '" + fullyQualifiedFxmlName + "'.");
        }
        assertFullyQualified(fullFxmlResourceName);
    }

    @Override
    public URL fxmlResource() {
        return getFullyQualifiedResource(fullFxmlResourceName);
    }

    @Override
    public URL cssResource() {
        return getFullyQualifiedResource(cssResourceName());
    }

    @VisibleForTesting
    String cssResourceName() {
        return fullFxmlResourceName.substring(0, fullFxmlResourceName.length() - FXML_SUFFIX.length()) + CSS_SUFFIX;
    }

    private URL getFullyQualifiedResource(String name) {
        assertFullyQualified(name);
        return ResourceBasedFxmlLoadingConfiguration.class.getResource(name);
    }

    @Override
    public String toString() {
        return "ResourceBasedFxmlLoadingConfiguration [fullFxmlResourceName=" + fullFxmlResourceName + "]";
    }

    private static void assertFullyQualified(String name) {
        if (!name.startsWith("/")) {
            throw new IllegalArgumentException(
                    "resource name must be fully qualified (start with '/') . This is not the case for given name '"
                            + name + "'.");
        }
    }

    public static ResourceBasedFxmlLoadingConfiguration fromFxml(String fullyQualifiedResourceName) {
        return new ResourceBasedFxmlLoadingConfiguration(fullyQualifiedResourceName);
    }

    @Override
    public String conventionalName() {
        return simpleFileName().replaceAll(FXML_SUFFIX, "");
    }

    private String simpleFileName() {
        int lastSlash = fullFxmlResourceName.lastIndexOf('/');
        return fullFxmlResourceName.substring(lastSlash + 1);
    }

    @Override
    public String packageName() {
        return fullFxmlResourceName.substring(1, fullFxmlResourceName.length() - simpleFileName().length() - 1)
                .replace('/', '.');
    }

}
