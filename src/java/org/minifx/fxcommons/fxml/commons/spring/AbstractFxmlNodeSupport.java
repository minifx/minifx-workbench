/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class AbstractFxmlNodeSupport implements FxmlNodeSupport {

    @Autowired
    private FxmlNodeService nodeService;

    @Override
    public OngoingNestedNodeCreation nestedFxmlNode() {
        return new OngoingNestedNodeCreation(nodeService);
    }

    @Override
    public OngoingNonNestedNodeCreation nonNestedFxmlNode() {
        return new OngoingNonNestedNodeCreation(nodeService);
    }

}
