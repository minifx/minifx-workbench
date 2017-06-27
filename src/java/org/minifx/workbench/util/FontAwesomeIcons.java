/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public final class FontAwesomeIcons {

    private FontAwesomeIcons() {
        /* static methods */
    }

    private static final int DEFAULT_ICON_SIZE = 32;

    public static Text getFontAwesome(FontAwesomeIcon icon) {
        return getFontAwesome(icon, DEFAULT_ICON_SIZE, Color.BLACK);
    }

    public static Text getFontAwesome(FontAwesomeIcon icon, int size) {
        return getFontAwesome(icon, size, Color.BLACK);
    }

    public static Text getFontAwesome(FontAwesomeIcon icon, int size, Color color) {
        Text textIcon = FontAwesomeIconFactory.get().createIcon(icon, Integer.toString(size));
        textIcon.setFill(color);
        return textIcon;
    }

    public static Text getFontAwesome(FontAwesomeIcon icon, Color color) {
        return getFontAwesome(icon, DEFAULT_ICON_SIZE, color);
    }
}
