/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import org.minifx.workbench.annotations.Icon;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Static methods for creating icons
 *
 * @author kfuchsbe
 */
public final class Icons {

    private Icons() {
        /* Only static methods */
    }

    /**
     * Creates a JavaFx node as specified from the given icon annotation and the given size
     *
     * @param iconAnnotation the icon annotation to process
     * @param size the size of the icon
     * @return a node to be put into java fx, showing the icon
     */
    public static Node graphicFrom(Icon iconAnnotation, String size) {
        FontAwesomeIcon icon = iconAnnotation.value();
        Text nodeIcon = FontAwesomeIconFactory.get().createIcon(icon, size);
        nodeIcon.setFill(color(iconAnnotation));
        return nodeIcon;
    }

    private static Color color(Icon iconAnnotation) {
        return Color.valueOf(iconAnnotation.color().trim());
    }

    public static Node graphicFrom(FontAwesomeIcon icon, String size, Color color) {
        Text nodeIcon = FontAwesomeIconFactory.get().createIcon(icon, size);
        nodeIcon.setFill(color);
        return nodeIcon;
    }

}
