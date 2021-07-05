/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.minifx.workbench.domain.PerspectivePos.CENTER;
import static org.minifx.workbench.util.Purpose.PERSPECTIVE;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.util.Optional;

import org.controlsfx.glyphfont.FontAwesome;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.NoGutters;
import org.minifx.workbench.domain.DefaultPerspective;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.definition.DisplayProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Utility methods for perspective classes.
 *
 * @author kfuchsbe
 */
public final class Perspectives {

    public static final PerspectivePos DEFAULT_POSITION = CENTER;
    public static final Class<? extends Perspective> DEFAULT_PERSPECTIVE = DefaultPerspective.class;

    private static final Color DEFAULT_COLOR = Color.rgb(2, 2, 2);
    private static final FontAwesome.Glyph DEFAULT_PERSPECTIVE_ICON = FontAwesome.Glyph.QUESTION;

    private Perspectives() {
        /* Only static methods */
    }

    /**
     * Retrieves the display properties from the given perspective.
     *
     * @param perspective the perspective (class) from which to retrieve properties
     * @return the retrieved properties
     */
    public static DisplayProperties perspectiveDisplayProperties(Class<? extends Perspective> perspective) {
        return new DisplayProperties(perspectiveNameFrom(perspective), perspectiveGraphics(perspective),
                orderFrom(perspective), hasGutters(perspective));
    }

    private static boolean hasGutters(Class<? extends Perspective> perspective) {
        return perspective.getAnnotation(NoGutters.class) == null;
    }


    private static final int orderFrom(Class<?> perspectiveClass) {
        requireNonNull(perspectiveClass, "perspectiveClass must not be null.");
        Order order = perspectiveClass.getAnnotation(Order.class);
        return ofNullable(order).map(Order::value).orElse(LOWEST_PRECEDENCE);
    }

    public static Node perspectiveDefaultGraphics() {
        return Icons.graphicFrom(Perspectives.DEFAULT_PERSPECTIVE_ICON, PERSPECTIVE.getSizeScaling(), Perspectives.DEFAULT_COLOR);
    }

    private static Node perspectiveGraphics(Class<? extends Perspective> perspective) {
        return Optional.ofNullable(perspective.getAnnotation(Icon.class))
                .<Node>map(ic -> Icons.graphicFrom(ic, PERSPECTIVE.getSizeScaling()))
                .orElseGet(Perspectives::perspectiveDefaultGraphics);
    }

    private static String perspectiveNameFrom(Class<?> perspective) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(perspective, Name.class)).map(Name::value)
                .orElse(perspective.getSimpleName());
    }

}
