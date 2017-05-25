/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import org.minifx.workbench.annotations.Footer;
import org.springframework.stereotype.Component;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

@Component
@Footer
public class SimpleFooter extends BorderPane {

    public SimpleFooter() {
        setCenter(new Label("Footer!"));
    }

}
