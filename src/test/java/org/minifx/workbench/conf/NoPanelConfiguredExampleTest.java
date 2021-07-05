/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minifx.workbench.conf.MiniFxWorkbenchConfiguration.ID_MAIN_PANEL;
import static org.minifx.workbench.util.Tests.idRef;
import static org.minifx.workbench.util.Tests.single;

import org.junit.Test;

import javafx.scene.Node;

public class NoPanelConfiguredExampleTest extends AbstractSpringApplicationTest {

    public NoPanelConfiguredExampleTest() {
        super(MiniFxWorkbenchSceneConfiguration.class);
    }

    @Test
    public void mainPanelExists() {
        Node node = single(lookup(idRef(ID_MAIN_PANEL)));
        assertThat(node).isNotNull();
    }

}
