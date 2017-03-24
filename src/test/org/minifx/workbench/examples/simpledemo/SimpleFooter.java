/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import org.minifx.workbench.domain.WorkbenchFooter;
import org.springframework.stereotype.Component;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

@Component
public class SimpleFooter extends BorderPane implements WorkbenchFooter {

    public SimpleFooter() {
        setCenter(new Label("Footer!"));
    }
    
}
