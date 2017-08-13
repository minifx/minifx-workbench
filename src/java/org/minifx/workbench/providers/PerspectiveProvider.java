/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.providers;

import java.util.Set;

import org.minifx.workbench.domain.definition.PerspectiveDefinition;

public interface PerspectiveProvider {

    Set<PerspectiveDefinition> perspectives();

}
