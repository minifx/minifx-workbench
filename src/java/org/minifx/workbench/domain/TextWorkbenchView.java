/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TextWorkbenchView extends BorderPane implements WorkbenchView {

    public TextWorkbenchView(String text) {
        setCenter(new Label(text));
        setPadding(new Insets(10));
    }

}
