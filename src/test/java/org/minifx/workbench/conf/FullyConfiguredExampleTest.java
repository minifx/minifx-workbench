/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minifx.workbench.util.Tests.idRef;
import static org.minifx.workbench.util.Tests.single;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.minifx.workbench.conf.fullyconfigured.FullExampleConfiguration;
import org.minifx.workbench.conf.fullyconfigured.P1CenterA;
import org.minifx.workbench.conf.fullyconfigured.Perspective1;
import org.minifx.workbench.conf.fullyconfigured.Perspective2;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

@Ignore("re-check with more time testfx")
public class FullyConfiguredExampleTest extends AbstractSpringApplicationTest {

    public FullyConfiguredExampleTest() {
        super(MiniFxWorkbenchConfiguration.class, FullExampleConfiguration.class);
    }

    @Before
    public void setUp() {
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void centerViewIsAvailable() {
        Node centerView = single(lookup(idRef(P1CenterA.ID)));
        assertThat(centerView).isNotNull();
    }

    @Test
    public void perspectiveOneIsSelected() {
        ToggleButton perspectiveOneButton = (ToggleButton) single(lookup(Perspective1.LABEL));
        assertThat(perspectiveOneButton.isSelected()).isTrue();
    }

    @Test
    public void perspectiveTwoIsNotSelected() {
        assertThat(perspectiveTwoButton().isSelected()).isFalse();
    }

    @Test
    public void clickOnPerspectiveTwoSelectsIt() {
        clickOn(Perspective2.LABEL);
        assertThat(perspectiveTwoButton().isSelected()).isTrue();

    }

    private ToggleButton perspectiveTwoButton() {
        return (ToggleButton) single(lookup(Perspective2.LABEL));
    }

}
