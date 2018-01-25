/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class FxmlControllerConventionTest {

    @Test
    public void test() {
        FxmlControllerConvention convention = FxmlControllerConvention.of(FxmlControllerConventionTest.class);
        System.out.println(convention.bundleName());

    }

}
