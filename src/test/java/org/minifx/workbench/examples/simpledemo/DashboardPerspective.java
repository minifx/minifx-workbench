/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;

import static org.controlsfx.glyphfont.FontAwesome.Glyph.AMBULANCE;

@Name("Dashboard")
@Icon(value = AMBULANCE, color = "green")
public interface DashboardPerspective extends Perspective {
    /* marker */
}
