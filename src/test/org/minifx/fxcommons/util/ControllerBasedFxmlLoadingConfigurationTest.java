/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.minifx.fxcommons.fxml.commons.ControllerBasedFxmlLoadingConfiguration;
import org.minifx.fxcommons.fxml.commons.FxmlLoadingConfiguration;

public class ControllerBasedFxmlLoadingConfigurationTest {

    @Test
    public void test() {
        FxmlLoadingConfiguration convention = ControllerBasedFxmlLoadingConfiguration.of(ControllerBasedFxmlLoadingConfigurationTest.class);
       // System.out.println(convention.bundleName());

    }

}
