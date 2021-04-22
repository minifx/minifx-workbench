/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

import static org.controlsfx.glyphfont.FontAwesome.Glyph.WHATSAPP;

@Order(1)
@Name(Perspective1.LABEL)
@Icon(value = WHATSAPP, color = "blue")
public interface Perspective1 extends Perspective {

    String LABEL = "Perspective 1";

}