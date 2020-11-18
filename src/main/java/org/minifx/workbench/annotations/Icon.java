/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.annotations;

import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.minifx.workbench.domain.Perspective;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for specifying the Icon of a MiniFx view or {@link Perspective}. NOTE: the {@link #color()} String must
 * conform to the rules of JavaFX {@link Color#valueOf(String)} method.
 *
 * @author acalia
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Icon {

    public static final String DEFAULT_COLOR = "#38678e";

    FontAwesome.Glyph value();

    String color() default DEFAULT_COLOR;

}
