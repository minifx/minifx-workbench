/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons.fxml.commons;

import org.minifx.fxcommons.fxml.util.UniqueUsage;

public final class FxmlControllers {

    private static final UniqueUsage UNIQUE_CONTROLLERS = new UniqueUsage();

    public static final <T> T useOnceOrThrow(T object) {
        return UNIQUE_CONTROLLERS.useOnceOrThrow(object, "The controller " + object
                + " would going to be used for another fxml-defined element. This is not allowed. "
                + "All controllers must be prototypes. "
                + "In case you are using spring, make sure that controllers which have to be looked up are declared as prototypes.");
    }

    public static final boolean isUsed(Object object) {
        return UNIQUE_CONTROLLERS.isUsed(object);
    }

}
