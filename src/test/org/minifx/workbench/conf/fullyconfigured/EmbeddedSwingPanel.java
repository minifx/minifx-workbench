/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.minifx.workbench.annotations.View;
import org.springframework.core.annotation.Order;

@Order(1)
@View(in = Perspective1.class, at = CENTER)
public class EmbeddedSwingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public EmbeddedSwingPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("I am a swing view"), BorderLayout.CENTER);
    }

}
