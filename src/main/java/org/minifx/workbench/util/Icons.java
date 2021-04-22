/*
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.minifx.workbench.annotations.Icon;

import java.util.Optional;

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
     * @param sizeFactor     the size of the icon
     * @return a node to be put into java fx, showing the icon
     */
    public static Glyph graphicFrom(Icon iconAnnotation, double sizeFactor) {
        FontAwesome.Glyph icon = iconAnnotation.value();
        return applySizeFactor(graphicFrom(icon).color(color(iconAnnotation)), sizeFactor);
    }

    /**
     * Materialize the given icon into something displayable
     *
     * @param icon to materialize
     * @return the {@link Glyph} that can be added to the GUI
     */
    public static Glyph graphicFrom(FontAwesome.Glyph icon) {
        return GlyphFontRegistry.font("FontAwesome").create(icon);
    }

    public static Glyph graphicFrom(FontAwesome.Glyph icon, double sizeFactor, Color color) {
        return applySizeFactor(graphicFrom(icon).color(color), sizeFactor);
    }

    private static Glyph applySizeFactor(Glyph glyph, double sizeFactor) {
        Optional.ofNullable(GlyphFontRegistry.font(glyph.getFont().getFamily()))
                .ifPresent(glyphFont -> glyph.setFontSize(glyphFont.getDefaultSize() * sizeFactor));
        return glyph;
    }

    private static Color color(Icon iconAnnotation) {
        return Color.valueOf(iconAnnotation.color().trim());
    }

}
