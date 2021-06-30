/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import static org.controlsfx.glyphfont.FontAwesome.Glyph.RANDOM;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;

@Name("Default perspective")
@Icon(RANDOM)
public interface DefaultPerspective extends Perspective {
    /* Default perspective */
}
