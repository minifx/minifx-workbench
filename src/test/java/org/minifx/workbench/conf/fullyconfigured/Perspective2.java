/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

@Order(2)
@Name(Perspective2.LABEL)
@Icon(value = FontAwesomeIcon.ANDROID, color = "green")
public interface Perspective2 extends Perspective {

    public static final String LABEL = "Perspective 2";
}