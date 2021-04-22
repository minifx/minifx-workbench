package org.minifx.workbench.examples.simpledemo;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.controlsfx.glyphfont.FontAwesome.Glyph.BARS;
import static org.minifx.workbench.domain.PerspectivePos.CENTER;

@Icon(color = "darkred", value = BARS)
@Name("Coupling measurement with ADT excitation")
@View(in = NoGuttersPerspective.class, at = CENTER)
@Order(0)
@Component
public class SimpleLabelView extends BorderPane {

    public SimpleLabelView() {
        setCenter(new Label("Simple Label"));
    }
}
