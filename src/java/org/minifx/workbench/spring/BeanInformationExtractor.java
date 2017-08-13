/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import org.minifx.workbench.domain.definition.DisplayProperties;
import org.minifx.workbench.util.Purpose;

import javafx.scene.paint.Color;

public interface BeanInformationExtractor {

    DisplayProperties displayPropertiesFrom(Object view, Purpose purpose);

    /*
     * Not sure if this should be exposed... For the moment this is required for the (dependant)
     * AnalysisPerspectiveProvider ... but it feels more like a hack ... Probably the displayProperties object should
     * expose an iconColor ...?? To be seen ;-)
     */
    Color iconColor(Object view);

}
