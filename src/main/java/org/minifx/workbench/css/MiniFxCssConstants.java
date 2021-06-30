/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.css;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

public final class MiniFxCssConstants {

    private static final String STYLES_SUBDIR = "styles";
    private static final List<String> BARE_CSS_STYLES = asList("constants.css", "minifxPerspectiveStyle.css",
            "minifxTabPaneStyle.css", "minifxToolbarStyle.css", "minifxSplitPaneStyle.css", "minifxButtonStyle.css",
            "minifxTreeViewStyle.css");

    public static final String MAIN_PANE_CLASS = "main-panel";
    public static final String COMPONENTS_OF_MAIN_PANEL_CLASS = "main-panel-view";
    public static final String COMPONENTS_OF_MAIN_PANEL_CLASS_NO_GUTTERS = "main-panel-view-no-gutters";
    public static final String SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS = "single-main-panel-view";
    public static final Collection<String> CSS_LOCATIONS = cssStyleLocations();

    public static final String PERSPECTIVE_BUTTON_CLASS = "perspective-button";
    public static final String TOOLBAR_BUTTON_CLASS = "toolbar-button";

    private MiniFxCssConstants() {
        /* Just constants */
    }

    @VisibleForTesting
    static final String path() {
        return MiniFxCssConstants.class.getPackage().getName().replace(".", "/") + "/" + STYLES_SUBDIR;
    }

    private static List<String> cssStyleLocations() {
        return BARE_CSS_STYLES.stream().map(name -> path() + "/" + name).collect(toList());
    }

}
