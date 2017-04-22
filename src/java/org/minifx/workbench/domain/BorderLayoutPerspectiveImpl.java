/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import java.util.List;

import javafx.scene.Node;

/**
 * Internal API. Represents the real manifestation of a perspective
 * 
 * @author kfuchsbe
 */
public class BorderLayoutPerspectiveImpl extends AbstractPerspectiveInstance {

    public BorderLayoutPerspectiveImpl(String name, Node graphic, List<WorkbenchView> views, int order) {
        super(name, graphic, views, order);
    }

}
