/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.css;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MiniFxCssConstantsTest {

    private static final String PACKAGE_PATH = "org/minifx/workbench/css/styles";

    @Test
    public void getPackagePath() {
        assertThat(MiniFxCssConstants.path()).isEqualTo(PACKAGE_PATH);
    }

    @Test
    public void cssLocationsAreCorrect() {
        assertThat(MiniFxCssConstants.CSS_LOCATIONS).allMatch(name -> name.startsWith(PACKAGE_PATH + "/"));
    }

}
