/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import org.junit.Test;

public class FxmlNodeBuilderTest {

    @Test
    public void usageExamples() {
        FxmlNodeBuilder builder = FxmlNodeBuilder.byConventionFrom(new Object());
        if (builder.canBuild()) {
            builder.build();
        }

        builder.build();

    }

}
